package com.unicornstudio.lanball.network.server.util;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.server.dto.Player;

import java.util.Map;

public class ServerUtils {

    public static void propagateData(NetworkObject networkObject, Map<Connection, Player> players, Connection initializerConnection) {
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(initializerConnection))
                .map(Map.Entry::getKey)
                .forEach(connection -> connection.sendUDP(networkObject));
    }

}
