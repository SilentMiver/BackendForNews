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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
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
    public List<NewDTO> searchAll(String q) {
        try {
            String encodedQuery = URLEncoder.encode(q, StandardCharsets.UTF_8);
            HttpResponse httpResponse = httpClient.execute(new HttpGet(String.format(url, encodedQuery, 0, "day")));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                NewsApiResponseDTO responseDTO = gson.fromJson(EntityUtils.toString(entity), NewsApiResponseDTO.class);
                newRepository.saveAll(responseDTO.getNews().stream().map((r) -> modelMapper.map(r, New.class)).collect(Collectors.toList()));
                EntityUtils.consume(entity);
                return responseDTO.getNews();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<NewDTO> search(String query) {
        return Optional.empty();
    }

    @Override
    public List<NewDTO> findAll(String query) {
        return null;
    }

    @Override
    public Optional<NewDTO> find(String query) {
        return Optional.empty();
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
