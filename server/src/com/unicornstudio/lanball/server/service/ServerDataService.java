package com.unicornstudio.lanball.server.service;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.commons.GameTimer;
import com.unicornstudio.lanball.network.model.Ball;
import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.server.data.ServerData;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Singleton
public class ServerDataService {

    private final ServerData serverData = new ServerData();

    private final Map<Connection, Player> players = new HashMap<>();

    @Setter
    private byte[] mapData;

    private final Ball ball = new Ball();

    private GameState gameState = GameState.LOBBY;

    private Integer timeLimitSelectBoxIndex;

    private Integer scoreLimitSelectBoxIndex;

    @Setter
    private Integer team1Score = 0;

    @Setter
    private Integer team2Score = 0;

    private Integer scoreLimit;

    private GameTimer timer;

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

    public void updateBall(Float positionX, Float positionY, Float velocityX, Float velocityY) {
        ball.setPositionX(positionX);
        ball.setPositionY(positionY);
        ball.setVelocityX(velocityX);
        ball.setVelocityY(velocityY);
    }

    public void setTimeLimitSelectBoxIndex(Integer timeLimitSelectBoxIndex) {
        this.timeLimitSelectBoxIndex = timeLimitSelectBoxIndex;
        createNewTimer();
    }

    public void createNewTimer() {
        timer = new GameTimer((timeLimitSelectBoxIndex + 1) * 60 * 1000);
        timer.setPause(!gameState.equals(GameState.IN_PROGRESS));
    }

    public void setScoreLimitSelectBoxIndex(Integer scoreLimitSelectBoxIndex) {
        this.scoreLimitSelectBoxIndex = scoreLimitSelectBoxIndex;
        scoreLimit = scoreLimitSelectBoxIndex + 1;
    }

    public void clear() {
        players.clear();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (timer != null) {
            timer.setPause(!gameState.equals(GameState.IN_PROGRESS));
        }
    }

    public long getTimerTime() {
        if (timer == null) {
            return 0L;
        }
        return timer.getTime();
    }

}
