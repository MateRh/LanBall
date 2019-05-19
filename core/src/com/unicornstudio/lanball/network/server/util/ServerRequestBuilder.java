package com.unicornstudio.lanball.network.server.util;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.GameStartServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MatchEndServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerSetStartPositionServerRequest;
import com.unicornstudio.lanball.network.protocol.request.RemotePlayerServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ScoreUpdateRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ServerStateServerRequest;
import com.unicornstudio.lanball.network.server.dto.Ball;
import com.unicornstudio.lanball.network.server.dto.MatchEndReason;
import com.unicornstudio.lanball.network.server.dto.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerRequestBuilder {

    public static RemotePlayerServerRequest createRemotePlayerRequest(Player player, boolean remotePlayer) {
        RemotePlayerServerRequest request = new RemotePlayerServerRequest();
        request.setId(player.getId());
        request.setName(player.getName());
        request.setTeamType(player.getTeamType());
        request.setRemotePlayer(remotePlayer);
        return request;
    }

    public static PlayerUpdateServerRequest createPlayerUpdateServerRequest(Player player, boolean remote) {
        PlayerUpdateServerRequest request = new PlayerUpdateServerRequest();
        request.setId(player.getId());
        request.setRemote(remote);
        request.setPositionX(player.getPositionX());
        request.setPositionY(player.getPositionY());
        request.setVelocityX(player.getVelocityX());
        request.setVelocityY(player.getVelocityY());
        return request;
    }

    public static PlayerSetStartPositionServerRequest createPlayerSetStartPositionServerRequest(Player player, boolean remote, TeamType teamType, int positionId) {
        PlayerSetStartPositionServerRequest request = new PlayerSetStartPositionServerRequest();
        request.setId(player.getId());
        request.setRemote(remote);
        request.setTeamType(teamType);
        request.setPositionId(positionId);
        return request;
    }

    public static ServerStateServerRequest createServerStateServerRequest(Set<Player> players, Player player, GameState state,
            Integer scoreLimitSelectedIndex, Integer timeLimitSelectedIndex) {
        ServerStateServerRequest serverStateServerRequest = new ServerStateServerRequest();
        serverStateServerRequest.setGameState(state);
        serverStateServerRequest.setPlayers(
                players.stream()
                        .map(p -> ServerRequestBuilder.createRemotePlayerRequest(p, p.equals(player)))
                        .collect(Collectors.toSet())
        );
        serverStateServerRequest.setScoreLimitSelectBoxIndex(scoreLimitSelectedIndex);
        serverStateServerRequest.setTimeLimitSelectBoxIndex(timeLimitSelectedIndex);
        return serverStateServerRequest;
    }

    public static BallUpdateServerRequest createBallUpdateServerRequest(Ball ball) {
        return new BallUpdateServerRequest(ball.getPositionX(),
                ball.getPositionY(),
                ball.getVelocityX(),
                ball.getVelocityY()
        );
    }

    public static MapLoadServerRequest createMapLoadServerRequest(byte[] mapData) {
        return new MapLoadServerRequest(mapData);
    }

    public static GameStartServerRequest createGameStartServerRequest() {
        return new GameStartServerRequest();
    }

    public static PlayerChangeTeamServerRequest createPlayerChangeTeamServerRequest(Player player) {
        return new PlayerChangeTeamServerRequest(player.getId(), player.getTeamType());
    }

    public static SelectBoxUpdateServerRequest createSelectBoxUpdateServerRequest(String selectBoxName, Integer selectedIndex) {
        return new SelectBoxUpdateServerRequest(selectBoxName, selectedIndex);
    }

    public static PlayerKickBallServerRequest createPlayerKickBallServerRequest(Integer playerId, Float forceX, Float forceY, Float pointX, Float pointY) {
        return new PlayerKickBallServerRequest(playerId, forceX, forceY, pointX, pointY);
    }

    public static MatchEndServerRequest createMatchEndServerRequest(MatchEndReason endReason) {
        return new MatchEndServerRequest(endReason);
    }

    public static ScoreUpdateRequest createScoreUpdateRequest(TeamType teamType, Integer score) {
        return new ScoreUpdateRequest(teamType, score);
    }

}
