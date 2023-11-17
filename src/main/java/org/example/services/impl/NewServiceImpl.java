package org.example.services.impl;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.example.dtos.NewDTO;
import org.example.dtos.NewsApiResponseDTO;
import org.example.models.New;
import org.example.repositories.NewRepository;
import org.example.services.NewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewServiceImpl implements NewService {
    private NewRepository newRepository;
    private final ModelMapper modelMapper;
    private final HttpClient httpClient;
    private final Gson gson;
    private static final String url = "https://mediametrics.ru/satellites/api/search/?ac=search&nolimit=1&q=%s&p=%s&d=%s&callback=JSON";

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
    public List<NewDTO> searchAll(String query) {
        List<NewDTO> result;
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(String.format(url, URLEncoder.encode(query, StandardCharsets.UTF_8), 0, "day")));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                NewsApiResponseDTO responseDTO = gson.fromJson(EntityUtils.toString(entity), NewsApiResponseDTO.class);
                int iter = Math.ceilDiv(responseDTO.getTotal(), 20);
                result = new ArrayList<>(iter * 20);
                newRepository.saveAll(responseDTO.getNews()
                        .stream()
                        .map((r) -> modelMapper.map(r, New.class))
                        .collect(Collectors.toList()));
                result.addAll(responseDTO.getNews());
                EntityUtils.consume(entity);
                for (int i = 1; i < iter; i++) {
                    result.addAll(search(query, i));
                }
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public List<NewDTO> search(String query, int page) {
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(String.format(url, URLEncoder.encode(query, StandardCharsets.UTF_8), page, "day")));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                NewsApiResponseDTO responseDTO = gson.fromJson(EntityUtils.toString(entity), NewsApiResponseDTO.class);
                newRepository.saveAll(responseDTO.getNews()
                        .stream()
                        .map((r) -> modelMapper.map(r, New.class))
                        .collect(Collectors.toList()));
                EntityUtils.consume(entity);
                return responseDTO.getNews();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }


    @Override
    public List<NewDTO> findAll(String query, Sort sort) {
        List<New> result = newRepository.findByTitleRegexIgnoreCase(".*" + query + ".*", sort);
        return result
                .stream()
                .map((n) -> modelMapper.map(n, NewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NewDTO> findAll(String query) {
        List<New> result = newRepository.findByTitleRegexIgnoreCase(".*" + query + ".*");
        System.out.println(result.size());
        return result
                .stream()
                .map((n) -> modelMapper.map(n, NewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NewDTO> find(String query, int page, Sort sort) {
        return newRepository.findPageByTitleRegexIgnoreCase(".*" + query + ".*", PageRequest.of(page, 20).withSort(sort))
                .map((n) -> modelMapper.map(n, NewDTO.class))
                .toList();
    }

    @Override
    public List<NewDTO> find(String query, int page) {
        return newRepository.findPageByTitleRegexIgnoreCase(".*" + query + ".*", PageRequest.of(page, 20))
                .map((n) -> modelMapper.map(n, NewDTO.class))
                .toList();
    }


    @Override
    public List<NewDTO> findRatingAll() {
        return null;
    }

    @Override
    public List<NewDTO> searchRatingAll() {
        return null;
    }
}
