package com.paf.exercise.exercise.service;

import com.paf.exercise.exercise.dto.telegram.BaseResponse;
import com.paf.exercise.exercise.dto.telegram.Message;
import com.paf.exercise.exercise.dto.telegram.SendMessageRequest;
import com.paf.exercise.exercise.dto.telegram.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramClient {

    private RestTemplate restTemplate;

    @Value("${telegram.base.url}")
    private String telegramBaseUrl;

    @PostConstruct
    public void init() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        builder.setConnectTimeout(20000);
        builder.setReadTimeout(30000);

        restTemplate = builder.build();
    }

    public Message sendMessage(SendMessageRequest sendMessageRequest) {
        HttpEntity<SendMessageRequest> req = new HttpEntity<>(sendMessageRequest);

        return restTemplate.exchange(telegramBaseUrl + "/sendMessage",
                HttpMethod.POST, req,
                new ParameterizedTypeReference<BaseResponse<Message>>() {
                }).getBody().getResult();
    }

    public List<Update> getUpdates(long offset, int limit) {
        return restTemplate.exchange(telegramBaseUrl + "/getUpdates?limit=" + limit + "&offset=" + offset,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<BaseResponse<List<Update>>>() {
                }).getBody().getResult();
    }
}
