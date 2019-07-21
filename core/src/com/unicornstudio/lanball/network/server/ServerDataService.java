package com.unicornstudio.lanball.network.server;

import com.unicornstudio.lanball.model.TeamType;
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
    private byte[] mapData;

    private Ball ball = new Ball();

    private Long timeLimit;

    private Long lastTimeMillis;

    @Setter
    private GameState gameState = GameState.LOBBY;

    private Integer timeLimitSelectBoxIndex;

    private Integer scoreLimitSelectBoxIndex;

    @Setter
    private Integer team1Score = 0;

    @Setter
    private Integer team2Score = 0;

    private Integer scoreLimit;

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

    public Set<Player> getPlayersSet() {
        return new HashSet<>(players.values());
    }

    public  Map<Connection, Player> getPlayersByTeamType(TeamType teamType) {
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
        timeLimit = (long) ((timeLimitSelectBoxIndex + 1) * 60 * 1000);
    }

    public void updateTimer() {
        if (lastTimeMillis == null) {
            lastTimeMillis = System.currentTimeMillis();
        }
        timeLimit = timeLimit - (System.currentTimeMillis() - lastTimeMillis);
        lastTimeMillis = System.currentTimeMillis();
    }

    public void setScoreLimitSelectBoxIndex(Integer scoreLimitSelectBoxIndex) {
        this.scoreLimitSelectBoxIndex = scoreLimitSelectBoxIndex;
        scoreLimit = scoreLimitSelectBoxIndex + 1;
    }

}
