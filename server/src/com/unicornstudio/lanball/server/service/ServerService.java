package com.unicornstudio.lanball.server.service;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.server.listener.ServerListener;
import lombok.Getter;

import java.io.IOException;

@Singleton
public class ServerService {

    private final ServerListener serverListener;

    private final ServerDataService serverDataService;

    private final Server server;

    @Getter
    private boolean running = false;

    @Inject
    public ServerService() {
        serverDataService = new ServerDataService();
        serverListener = new ServerListener(serverDataService);
        server = new Server();
        Log.set(Log.LEVEL_ERROR);
    }

    public boolean start(int port) {
        server.start();
        try {
            server.bind(port, port);
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        server.addListener(serverListener);
        running = true;
        return true;
    }

    public void stop() {
        if (running) {
            server.removeListener(serverListener);
            server.stop();
            running = false;
            serverDataService.clear();
        }
    }


}
