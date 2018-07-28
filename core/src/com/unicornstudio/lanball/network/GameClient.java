package com.unicornstudio.lanball.network;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class GameClient {

    private Client client;

    public void connect() {
        //System.out.println(client.discoverHost(54777, 5000).getHostAddress());
        Client client = new Client();
        client.start();
        try {
            client.connect(5000, "192.168.1.101", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameServer.SomeRequest request = new GameServer.SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);
    }
}
