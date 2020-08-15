package com.unicornstudio.lanball.server.service;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerService {

    private final Map<Connection, Player> players = new HashMap<>();

    public void addPlayer(Connection connection, Player player) {
        players.put(connection, player);
    }

    public void removePlayer(Connection connection, Player player) {
        players.remove(connection, player);
    }

    public Player getPlayerByConnection(Connection connection) {
        return players.getOrDefault(connection, null);
    }

    public Player getPlayerById(int id) {
        return players.values().stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Connection getConnectionByPlayer(Player player) {
        return players.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public Set<Player> getPlayersSet(Connection connection) {
        return players.entrySet().stream()
                .filter(set -> !set.getKey().equals(connection))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Player> getPlayersSet() {
        return new HashSet<>(players.values());
    }

    public Set<Connection> getConnectionsSet(Connection connection) {
        Set<Connection> connections = new HashSet<>(players.keySet());
        if (connection != null) {
            connections.remove(connection);
        }
        return connections;
    }

    public Map<Connection, Player> getPlayersByTeamType(TeamType teamType) {
        return players.entrySet().stream()
                .filter(entry -> entry.getValue().getTeamType().equals(teamType))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
