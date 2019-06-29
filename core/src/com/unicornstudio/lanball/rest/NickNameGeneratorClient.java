package com.unicornstudio.lanball.rest;


import org.apache.commons.lang3.RandomStringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

public class NickNameGeneratorClient {

    private static final String API_URL = "https://api.codetunnel.net/random-nick";
    private static final Integer LIMIT = 16;

    private final Client client;

    public NickNameGeneratorClient() {
        client = ClientBuilder.newClient();
    }

    public String getNickName() {
        NickNameResponse response = getNickNameResponse().orElse(null);
        if (response == null || response.getError() != null) {
            return generateFallbackName();
        }
        return response.getNickname();
    }

    private Optional<NickNameResponse> getNickNameResponse() {
        try {
            return Optional.ofNullable(client.target(API_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(buildRequest(), MediaType.APPLICATION_JSON), NickNameResponse.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private NickNameRequest buildRequest() {
        NickNameRequest request = new NickNameRequest();
        request.setSizeLimit(LIMIT);
        return request;
    }

    private String generateFallbackName() {
        return RandomStringUtils.random(LIMIT, true, true);
    }

}
