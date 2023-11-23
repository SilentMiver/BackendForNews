package org.example.services.impl;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.example.dtos.NewRatingDTO;
import org.example.exceptions.ServerException;
import org.example.models.NewRating;
import org.example.repositories.NewRatingRepository;
import org.example.services.NewRatingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "nrs")
public class NewRatingServiceImpl implements NewRatingService {
    private NewRatingRepository newRatingRepository;
    private final ModelMapper modelMapper;
    private final HttpClient httpClient;
    private final Gson gson;
    private static final String url = "https://mediametrics.ru/rating/ru/hour.json";

    @Autowired
    public NewRatingServiceImpl(ModelMapper modelMapper, HttpClient httpClient, Gson gson) {
        this.modelMapper = modelMapper;
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Autowired
    public void setNewRatingRepository(NewRatingRepository newRatingRepository) {
        this.newRatingRepository = newRatingRepository;
    }

    @Override
    @Cacheable(value = "findAll", unless = "#result==null")
    public List<NewRatingDTO> findRatingAll() {
        return newRatingRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map((n) -> modelMapper.map(n, NewRatingDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    @CacheEvict(value = "findAll")
    public List<NewRatingDTO> searchRatingAndSaveAll() {
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                List<NewRatingDTO> responseDTO = gson.fromJson(EntityUtils.toString(entity), new TypeToken<List<NewRatingDTO>>() {
                }.getType());
                deleteAll();
                newRatingRepository.saveAll(responseDTO
                        .stream()
                        .map(nr -> modelMapper.map(nr, NewRating.class))
                        .collect(Collectors.toList()));
                EntityUtils.consume(entity);
                return responseDTO;
            }
        } catch (IOException e) {
            throw new ServerException.HttpClientException("Server error");
        }
        return List.of();
    }

    @Override
    @CacheEvict(value = "findAll")
    public void deleteAll() {
        newRatingRepository.deleteAll();
    }

}
