package org.example.services.impl;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.example.dtos.NewRatingDTO;
import org.example.models.NewRating;
import org.example.repositories.NewRatingRepository;
import org.example.services.NewRatingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Service

public class NewRatingServiceImpl implements NewRatingService {
    private NewRatingRepository newRatingRepository;
    private final ModelMapper modelMapper;
    private final HttpClient httpClient;
    private final Gson gson;
    private static final String url = "https://mediametrics.ru/rating/ru/online.json";

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
        return null;
    }

    @Override
    public List<NewRatingDTO> searchRatingAll() {
        try {
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                List<NewRatingDTO> response = gson.fromJson(EntityUtils.toString(entity), new TypeToken<List<NewRatingDTO>>() {}.getType());
                newRatingRepository.saveAll(response
                        .stream()
                        .map(nr->modelMapper.map(nr, NewRating.class))
                        .collect(Collectors.toList()));
                EntityUtils.consume(entity);
                return response;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
