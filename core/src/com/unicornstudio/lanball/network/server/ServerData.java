package com.unicornstudio.lanball.network.server;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ServerData {

    @Getter
    private List<Player> players = new ArrayList<>();

    @Setter
    @Getter
    private String host;

    @Setter
    @Getter
    private Integer port;

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Optional<Player> findById(int id) {
        return players.stream()
                .filter(player -> player.getId() == id)
                .findAny();
    }

    public Optional<Player> findByName(String name) {
        return players.stream()
                .filter(player -> name.equals(player.getName()))
                .findFirst();
    }

}
