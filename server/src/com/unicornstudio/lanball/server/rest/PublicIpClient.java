package com.unicornstudio.lanball.server.rest;

import com.unicornstudio.lanball.server.util.LogUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Optional;

public class PublicIpClient {

    private static final String API_URL = "https://api.ipify.org?format=json";

    private final Client client;

    public PublicIpClient() {
        client = ClientBuilder.newClient();
    }

    public void asyncPrintPublicIp(int port) {
        new Thread(() -> LogUtil.printConsole("Server started with address: " + getPublicIp() + ":" + port)).start();
    }

    public String getPublicIp() {
        return getNickNameResponse()
                .map(PublicIpResponse::getIp)
                .orElse(null);
    }

    private Optional<PublicIpResponse> getNickNameResponse() {
        try {
            return Optional.ofNullable(client.target(API_URL)
                    .request()
                    .get(PublicIpResponse.class));
        } catch (Exception e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

}
