package com.unicornstudio.lanball.server.service;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;

import java.util.Set;

public class SenderService {

    public static void send(Set<Connection> receivers, NetworkObject request) {
        receivers.parallelStream()
                .forEach(connection -> connection.sendUDP(request));
    }

}
