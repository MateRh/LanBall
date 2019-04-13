package com.unicornstudio.lanball.network.server;

import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.server.dto.Ball;
import com.unicornstudio.lanball.network.server.dto.Player;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Singleton
public class ServerDataService {

    private Map<Connection, Player> players = new HashMap<>();

    @Setter
    private String mapData;

    private Ball ball = new Ball();

    private String host;

    private Integer port;

    @Setter
    private GameState gameState = GameState.IN_LOBBY;

    private Integer timeLimitSelectBoxIndex;

    @Setter
    private Integer scoreLimitSelectBoxIndex;

    private Integer timeLimit;

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

    public Set<Player> getPlayersSet(Connection connection) {
        return players.entrySet().stream()
                .filter(set -> !set.getKey().equals(connection))
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    public Set<Player> getPlayersSet( ) {
        return new HashSet<>(players.values());
    }

    public void updateBall(Float positionX, Float positionY, Float velocityX, Float velocityY) {
        ball.setPositionX(positionX);
        ball.setPositionY(positionY);
        ball.setVelocityX(velocityX);
        ball.setVelocityY(velocityY);
    }

    public void setTimeLimitSelectBoxIndex(Integer timeLimitSelectBoxIndex) {
        this.timeLimitSelectBoxIndex = timeLimitSelectBoxIndex;
        this.timeLimit = (timeLimitSelectBoxIndex + 1) * 1000;
    }

}
