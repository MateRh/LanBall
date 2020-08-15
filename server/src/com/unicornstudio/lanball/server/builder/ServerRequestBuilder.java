package com.unicornstudio.lanball.server.builder;

import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.network.model.enumeration.MatchEndReason;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.BallContact;
import com.unicornstudio.lanball.network.model.protocol.RoundReset;
import com.unicornstudio.lanball.network.model.protocol.ScoreUpdate;
import com.unicornstudio.lanball.network.model.protocol.server.GameStartServer;
import com.unicornstudio.lanball.network.model.protocol.server.MapLoadServer;
import com.unicornstudio.lanball.network.model.protocol.server.MatchEndServer;
import com.unicornstudio.lanball.network.model.protocol.server.PlayerChangeTeamServer;
import com.unicornstudio.lanball.network.model.protocol.server.PlayerKeyPressServer;
import com.unicornstudio.lanball.network.model.protocol.server.PlayerKickBallServer;
import com.unicornstudio.lanball.network.model.protocol.server.PlayerSetStartPositionServer;
import com.unicornstudio.lanball.network.model.protocol.server.RemotePlayerServer;
import com.unicornstudio.lanball.network.model.protocol.server.SelectBoxUpdateServer;
import com.unicornstudio.lanball.network.model.protocol.server.ServerStateServer;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerRequestBuilder {

    public static RemotePlayerServer createRemotePlayer(Player player, boolean remotePlayer) {
        RemotePlayerServer request = new RemotePlayerServer();
        request.setId(player.getId());
        request.setName(player.getName());
        request.setTeamType(player.getTeamType());
        request.setRemotePlayer(remotePlayer);
        return request;
    }

    public static PlayerSetStartPositionServer createPlayerSetStartPositionServer(Player player, boolean remote, TeamType teamType, int positionId) {
        PlayerSetStartPositionServer request = new PlayerSetStartPositionServer();
        request.setId(player.getId());
        request.setRemote(remote);
        request.setTeamType(teamType);
        request.setPositionId(positionId);
        return request;
    }

    public static ServerStateServer createServerStateServer(Set<Player> players, Player player, GameState state,
            Integer scoreLimitSelectedIndex, Integer timeLimitSelectedIndex, byte[] mapData) {
        ServerStateServer serverStateServer = new ServerStateServer();
        serverStateServer.setGameState(state);
        serverStateServer.setMapData(mapData);
        serverStateServer.setPlayers(
                players.stream()
                        .map(p -> ServerRequestBuilder.createRemotePlayer(p, p.equals(player)))
                        .collect(Collectors.toSet())
        );
        serverStateServer.setScoreLimitSelectBoxIndex(scoreLimitSelectedIndex);
        serverStateServer.setTimeLimitSelectBoxIndex(timeLimitSelectedIndex);
        return serverStateServer;
    }

    public static MapLoadServer createMapLoadServer(byte[] mapData) {
        return new MapLoadServer(mapData);
    }

    public static GameStartServer createGameStartServer() {
        return new GameStartServer();
    }

    public static PlayerChangeTeamServer createPlayerChangeTeamServer(Player player) {
        return new PlayerChangeTeamServer(player.getId(), player.getTeamType());
    }

    public static SelectBoxUpdateServer createSelectBoxUpdateServer(String selectBoxName, Integer selectedIndex) {
        return new SelectBoxUpdateServer(selectBoxName, selectedIndex);
    }

    public static PlayerKickBallServer createPlayerKickBallServer(Integer playerId, Float forceX, Float forceY, Float pointX, Float pointY) {
        return new PlayerKickBallServer(playerId, forceX, forceY, pointX, pointY);
    }

    public static MatchEndServer createMatchEndServer(MatchEndReason endReason) {
        return new MatchEndServer(endReason);
    }

    public static ScoreUpdate createScoreUpdate(TeamType teamType, Integer score) {
        return new ScoreUpdate(teamType, score);
    }

    public static RoundReset createRoundReset(TeamType teamType) {
        return new RoundReset(teamType);
    }

    public static BallContact createBallContact() {
        return new BallContact();
    }

    public static PlayerKeyPressServer createPlayerKeyPressServer(Integer playerId) {
        return new PlayerKeyPressServer(playerId);
    }

}
