package com.unicornstudio.lanball.server.data;

import com.esotericsoftware.kryonet.Connection;
import com.unicornstudio.lanball.commons.GameTimer;
import com.unicornstudio.lanball.network.model.Ball;
import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ServerData {

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

}
