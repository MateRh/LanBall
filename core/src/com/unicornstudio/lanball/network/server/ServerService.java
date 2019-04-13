package com.unicornstudio.lanball.network.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.common.NetworkClassRegisterer;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;
import lombok.Getter;

import java.io.IOException;

@Singleton
public class ServerService {

    @Inject
    private ClientService clientService;

    @Inject
    private ServerListener serverListener;

    private final Server server;

    @Getter
    private boolean running = false;


    public ServerService() {
        server = new Server();
        NetworkClassRegisterer.register(server.getKryo());
        Log.set(Log.LEVEL_ERROR);
    }

    public boolean start(int port) {
        server.start();
        try {
            server.bind(port, port);
        } catch (IOException e) {
            return false;
        }
        server.addListener(serverListener);
        boolean connected = clientService.connect("localhost:" + port, PlayerRole.HOST);
        if (!connected) {
            stop();
        }
        return connected;
    }

    public void stop() {
        if (running) {
            clientService.disconnect();
            server.stop();
            running = false;
        }
    }


}
