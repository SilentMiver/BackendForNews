package org.example.services.impl;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.example.dtos.NewDTO;
import org.example.dtos.NewsApiResponseDTO;
import org.example.exceptions.ServerException;
import org.example.models.New;
import org.example.repositories.NewRepository;
import org.example.services.NewService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "ns")
public class NewServiceImpl implements NewService {
    private NewRepository newRepository;
    private final ModelMapper modelMapper;
    private final HttpClient httpClient;
    private final Gson gson;
    private static final long ONE_HOUR = 3600000;
    private static final long ONE_WEEK = 604800000;
    private static final long ONE_DAY = 86400000;

    @Autowired
    public NewServiceImpl(ModelMapper modelMapper, HttpClient httpClient, Gson gson) {
        this.modelMapper = modelMapper;
        this.httpClient = httpClient;
        this.gson = gson;
    }


    @Autowired
    public void setNewRepository(NewRepository newRepository) {
        this.newRepository = newRepository;
    }


    @Override
    @CacheEvict(value = {"findSort", "find", "findAll", "findAllSort"}, allEntries = true)
    public List<NewDTO> searchAndSaveAll(String query, String day) {
        NewsApiResponseDTO responseDTO = searchNews(query, 0, day);
        int count = newRepository.countByTitleRegexIgnoreCaseAndTimestampGreaterThan(".*" + query + ".*", getTime(day));
        if (responseDTO == null || responseDTO.getNews() == null || responseDTO.getTotal() == 0)
            return List.of();
        saveNewsToDatabase(responseDTO.getNews());
        int iter = Math.ceilDiv((responseDTO.getTotal() - count), 20);
        List<NewDTO> result = new ArrayList<>(20 * iter);
        for (int i = 1; i <= iter; i++) {
            result.addAll(search(query, i, day));
        }
        saveNewsToDatabase(result);
        return findAll(query, Sort.by(Sort.Direction.DESC, "timestamp"), day);
    }

    private List<NewDTO> search(String query, int page, String day) {
        NewsApiResponseDTO responseDTO = searchNews(query, page, day);
        if (responseDTO == null || responseDTO.getNews() == null || responseDTO.getTotal() == 0)
            return List.of();
        return responseDTO.getNews();
    }

    @Override
    @CacheEvict(value = {"findSort", "find", "findAll", "findAllSort"}, allEntries = true)
    public List<NewDTO> searchAndSave(String query, int page, String day) {
        NewsApiResponseDTO responseDTO = searchNews(query, page, day);
        if (responseDTO == null || responseDTO.getNews() == null || responseDTO.getTotal() == 0)
            return List.of();
        saveNewsToDatabase(responseDTO.getNews());
        return responseDTO.getNews();
    }


    @Override
    @Cacheable(value = "findAllSort", unless = "#result==null", key = "#query+'_'+#sort+'_'+#day")
    public List<NewDTO> findAll(String query, Sort sort, String day) {
        return newRepository.findByTitleRegexAndTimestampGreaterThan(".*" + query + ".*", getTime(day), sort)
                .stream()
                .map(n -> modelMapper.map(n, NewDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "findAll", unless = "#result==null", key = "#query+'_'+#day")
    public List<NewDTO> findAll(String query, String day) {
        return newRepository.findByTitleRegexAndTimestampGreaterThan(".*" + query + ".*", getTime(day))
                .stream()
                .map(n -> modelMapper.map(n, NewDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "findSort", unless = "#result==null", key = "#query+'_'+#page+'_'+#sort+'_'+#day")
    public List<NewDTO> find(String query, int page, Sort sort, String day) {
        return newRepository.findPageByTitleRegexAndTimestampGreaterThan(".*" + query + ".*", getTime(day), PageRequest.of(page, 20).withSort(sort))
                .stream()
                .map(n -> modelMapper.map(n, NewDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "find", unless = "#result==null", key = "#query+'_'+#page+'_'+#day")
    public List<NewDTO> find(String query, int page, String day) {
        return newRepository.findPageByTitleRegexAndTimestampGreaterThan(".*" + query + ".*", getTime(day), PageRequest.of(page, 20))
                .stream()
                .map(n -> modelMapper.map(n, NewDTO.class))
                .toList();
    }

    @Override
    public List<NewDTO> get(String query, int page, String day) {
        if (newRepository.existsByTitleRegexIgnoreCaseAndTimestampGreaterThan(".*" + query + ".*", getTimeForCheckPage(day, page))) {
            return find(query, page, Sort.by(Sort.Direction.DESC, "timestamp"), day);
        } else {
            return searchAndSave(query, page, day);
        }

    }


    @Override
    public List<NewDTO> getAll(String query, String day) {
        if (newRepository.existsByTitleRegexIgnoreCaseAndTimestampGreaterThan(".*" + query + ".*", getTimeForCheckAll(day))) {
            return findAll(query, Sort.by(Sort.Direction.DESC, "timestamp"), day);
        } else {
            return searchAndSaveAll(query, day);
        }
    }


    private @Nullable NewsApiResponseDTO searchNews(String query, int page, String day) {
        try {
            String url = "https://mediametrics.ru/satellites/api/search/?ac=search&nolimit=1&q=%s&p=%s&d=%s&callback=JSON";
            HttpResponse httpResponse = httpClient.execute(new HttpGet(String.format(url, URLEncoder.encode(query, StandardCharsets.UTF_8), page, day)));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                NewsApiResponseDTO responseDTO = gson.fromJson(EntityUtils.toString(entity), NewsApiResponseDTO.class);
                EntityUtils.consume(entity);
                return responseDTO;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new ServerException.HttpClientException("Server error.");
        }
    }


    private void saveNewsToDatabase(@NotNull List<NewDTO> newsList) {
        newRepository.saveAll(newsList.stream().map(n -> modelMapper.map(n, New.class)).toList());
    }
 
    private long getTime(@NotNull String day) {
        long time = ONE_DAY;
        if (day.equalsIgnoreCase("week"))
            time = ONE_WEEK;

        return System.currentTimeMillis() - time;
    }

    private long getTimeForCheckPage(@NotNull String day, int page) {
        page++;
        if (day.equalsIgnoreCase("week")) {
            return System.currentTimeMillis() - (page * ONE_HOUR);
        } else {
            return System.currentTimeMillis() - (page * (ONE_HOUR / 2));
        }
    }

    private long getTimeForCheckAll(@NotNull String day) {
        if (day.equalsIgnoreCase("week")) {
            return System.currentTimeMillis() - (2 * ONE_HOUR);
        } else {
            return System.currentTimeMillis() - (ONE_HOUR / 2);
        }
    }


}
