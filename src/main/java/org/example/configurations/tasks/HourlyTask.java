//package org.example.configurations.tasks;
//
//import com.google.gson.Gson;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.util.EntityUtils;
//import org.example.dtos.NewRatingDTO;
//import org.example.models.NewRating;
//import org.example.repositories.NewRatingRepository;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@EnableScheduling
//@Component
//public class HourlyTask {
//    private final HttpClient httpClient;
//    private final ModelMapper modelMapper;
//    private final Gson gson;
//    private NewRatingRepository newRatingRepository;
//
//    @Autowired
//    public HourlyTask(HttpClient httpClient, Gson gson, ModelMapper modelMapper) {
//        this.httpClient = httpClient;
//        this.gson = gson;
//        this.modelMapper = modelMapper;
//    }
//
//    @Autowired
//    public void setNewRatingRepository(NewRatingRepository newRatingRepository) {
//        this.newRatingRepository = newRatingRepository;
//    }
//
//
//    @Scheduled(fixedRate = 3600000)
//    public void getRatingNews() {
//        try {
//            String url = "https://mediametrics.ru/rating/ru/hour.json";
//            HttpEntity entity = httpClient.execute(new HttpGet(url)).getEntity();
//            List<NewRatingDTO> response = gson.fromJson(EntityUtils.toString(entity), new TypeToken<List<NewRatingDTO>>() {}.getType());
//            EntityUtils.consume(entity);
//            newRatingRepository.saveAll(response
//                    .stream()
//                    .map((r) -> modelMapper.map(r, NewRating.class))
//                    .collect(Collectors.toList()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
