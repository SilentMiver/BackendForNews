package org.example.configurations;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackServerApplicationConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClients.createMinimal();
    }
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
