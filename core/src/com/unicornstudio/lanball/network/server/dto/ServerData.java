package com.unicornstudio.lanball.network.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServerData {

    private Map<Integer, Player> players = new HashMap<>();

    private String host;

    private Integer port;

    public void addPlayer(Integer id, Player player) {
        players.put(id, player);
    }

    public void removePlayer(Integer id, Player player) {
        players.remove(id, player);
    }

    public Player getPlayerById(Integer id) {
        return players.getOrDefault(id, null);
    }

}
