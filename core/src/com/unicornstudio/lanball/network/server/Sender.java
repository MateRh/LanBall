package com.unicornstudio.lanball.network.server;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.network.common.NetworkObject;

import java.util.Set;

public class Sender {

    public static void send(Set<Connection> receivers, NetworkObject request) {
        receivers.parallelStream()
                .forEach(connection -> connection.sendUDP(request));
    }

}
