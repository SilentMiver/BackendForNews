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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

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
    public List<NewRatingDTO> findRatingAll() {
        return newRatingRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map((n) -> modelMapper.map(n, NewRatingDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
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


    public void deleteAll() {
        newRatingRepository.deleteAll();
    }

}
