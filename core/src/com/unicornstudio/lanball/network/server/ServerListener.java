package com.unicornstudio.lanball.network.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.protocol.PlayerJoinClientRequest;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateClientRequest;
import com.unicornstudio.lanball.network.server.dto.Player;
import com.unicornstudio.lanball.network.server.util.ServerRequestBuilder;
import com.unicornstudio.lanball.network.server.util.ServerResponseBuilder;
import com.unicornstudio.lanball.network.server.util.ServerUtils;
import com.unicornstudio.lanball.prefernces.SettingsKeys;

import java.util.Optional;

@Singleton
public class ServerListener extends Listener {

    @Inject
    private ServerDataService serverDataService;

    public void connected (Connection connection) {
        if (serverDataService.getPlayerByConnection(connection) == null) {
            serverDataService.addPlayer(connection, new Player(connection.getID()));
        }
    }

    public void disconnected (Connection connection) {
        Optional.ofNullable(serverDataService.getPlayerByConnection(connection))
                .ifPresent(player -> serverDataService.removePlayer(connection, player));
    }

    public void received(Connection connection, Object object) {
        if (object instanceof NetworkObject){
            networkObjectReceived(connection, (NetworkObject) object);
        }
    }

    private void networkObjectReceived(Connection connection, NetworkObject networkObject) {
        Player player = serverDataService.getPlayerByConnection(connection);
        switch (networkObject.getType()) {
            case PLAYER_JOIN:
                onPlayerJoin(connection, player, (PlayerJoinClientRequest) networkObject);
                break;
            case PLAYER_UPDATE:
                onPlayerUpdate(connection, player, (PlayerUpdateClientRequest) networkObject);
                break;
            case GET_PLAYERS_LIST:
                onGetPlayersList(connection);
                break;
            case PLAYER_CHANGE_TEAM:
                onPlayerChangeTeam(player, (PlayerChangeTeamClientRequest) networkObject);
                break;
            case BALL_UPDATE:
                onBallUpdate(connection, (BallUpdateClientRequest) networkObject);
                break;
            case MAP_LOAD:
                onMapLoad(connection, (MapLoadClientRequest) networkObject);
                break;
            case START_GAME:
                onStartGame(connection);
                break;
            case SELECT_BOX_UPDATE:
                onSelectBoxUpdate((SelectBoxUpdateClientRequest) networkObject);
                break;
            case BALL_KICK:
                onBallKick((PlayerKickBallClientRequest) networkObject);
                break;
        }
    }

    private void onStartGame(Connection connection) {
        serverDataService.setGameState(GameState.IN_PROGRESS);
        ServerUtils.propagateData(ServerRequestBuilder.createGameStartServerRequest(), serverDataService.getPlayers(), null);
    }

    private void onMapLoad(Connection connection, MapLoadClientRequest request) {
        serverDataService.setMapData(request.getMapData());
        ServerUtils.propagateData(ServerRequestBuilder.createMapLoadServerRequest(request.getMapData()), serverDataService.getPlayers(), connection);
    }

    private void onBallUpdate(Connection connection, BallUpdateClientRequest request) {
        serverDataService.updateBall(request.getPositionX(), request.getPositionY(), request.getVelocityX(), request.getVelocityY());
        ServerUtils.propagateData(ServerRequestBuilder.createBallUpdateServerRequest(serverDataService.getBall()), serverDataService.getPlayers(), connection);
    }

    private void onPlayerJoin(Connection connection, Player player, PlayerJoinClientRequest object) {
        if (player == null) {
            return;
        }
        player.setName(object.getName());
        ServerUtils.propagateData(ServerRequestBuilder.createRemotePlayerRequest(player, false), serverDataService.getPlayers(), connection);
        connection.sendUDP(ServerRequestBuilder.createServerStateServerRequest(
                serverDataService.getPlayersSet(),
                player,
                serverDataService.getGameState(),
                serverDataService.getScoreLimitSelectBoxIndex(),
                serverDataService.getTimeLimitSelectBoxIndex()));
    }

    private void onPlayerUpdate(Connection connection, Player player, PlayerUpdateClientRequest object) {
        if (player == null) {
            return;
        }
        player.setPositionX(object.getPositionX());
        player.setPositionY(object.getPositionY());
        player.setVelocityX(object.getVelocityX());
        player.setVelocityY(object.getVelocityY());
        ServerUtils.propagateData(ServerRequestBuilder.createPlayerUpdateServerRequest(player), serverDataService.getPlayers(), connection);
    }

    private void onGetPlayersList(Connection connection) {
        connection.sendUDP(ServerResponseBuilder.createPlayersListNetworkObject(serverDataService.getPlayersSet()));
    }

    private void onPlayerChangeTeam(Player player, PlayerChangeTeamClientRequest object) {
        Player remotePlayer = serverDataService.getPlayerById(object.getId());
        if (remotePlayer != null) {
            remotePlayer.setTeamType(object.getTeamType());
            ServerUtils.propagateData(ServerRequestBuilder.createPlayerChangeTeamServerRequest(player), serverDataService.getPlayers(), null);
        }
    }

    private void onSelectBoxUpdate(SelectBoxUpdateClientRequest object) {
        if (object.getSelectBoxName().equals(SettingsKeys.TIME_LIMIT)) {
            serverDataService.setTimeLimitSelectBoxIndex(object.getSelectedIndex());
            System.out.println(object.getSelectBoxName() + " " + serverDataService.getTimeLimitSelectBoxIndex());
        }
        if (object.getSelectBoxName().equals(SettingsKeys.SCORE_LIMIT)) {
            serverDataService.setScoreLimitSelectBoxIndex(object.getSelectedIndex());
            System.out.println(object.getSelectBoxName() + " " + serverDataService.getScoreLimitSelectBoxIndex());
        }
        ServerUtils.propagateData(
                ServerRequestBuilder.createSelectBoxUpdateServerRequest(object.getSelectBoxName(), object.getSelectedIndex()),
                serverDataService.getPlayers(),
                null);
    }

    private void onBallKick(PlayerKickBallClientRequest object) {
        ServerUtils.propagateData(
                ServerRequestBuilder.createPlayerKickBallServerRequest(object.getPlayerId(), object.getForceX(), object.getForceY(), object.getPointX(), object.getPointY()),
                serverDataService.getPlayers(),
                null
        );
    }

}
