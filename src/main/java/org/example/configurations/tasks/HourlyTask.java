//package org.example.configurations.tasks;
//
//import com.google.gson.Gson;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.util.EntityUtils;
//import org.example.dtos.NewsApiResponseDTO;
//import org.example.models.New;
//import org.example.repositories.NewRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//
//@EnableScheduling
//@Component
//public class HourlyTask {
//    private final HttpClient httpClient;
//    private final ModelMapper modelMapper;
//    private final Gson gson;
//    private NewRepository newRepository;
//
//    @Autowired
//    public HourlyTask(HttpClient httpClient, Gson gson, ModelMapper modelMapper) {
//        this.httpClient = httpClient;
//        this.gson = gson;
//        this.modelMapper = modelMapper;
//    }
//
//    @Autowired
//    public void setNewRepository(NewRepository newRepository) {
//        this.newRepository = newRepository;
//    }
//
//    @Scheduled(fixedRate = 3600000)
//    public void getRatingNews() {
//        try {
//            String url = "https://mediametrics.ru/rating/ru/hour.json";
//            HttpEntity entity = httpClient.execute(new HttpGet(url)).getEntity();
//            NewsApiResponseDTO responseDTO = gson.fromJson(EntityUtils.toString(entity), NewsApiResponseDTO.class);
//            EntityUtils.consume(entity);
//            newRepository.saveAll(responseDTO.getNews().stream().map((r) -> modelMapper.map(r, New.class)).collect(Collectors.toList()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
