package com.unicornstudio.lanball.network.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkProtocol;
import com.unicornstudio.lanball.network.protocol.PlayerJoinClientRequest;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.GateContactClientRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerDisconnectRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKeyPressClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateClientRequest;
import com.unicornstudio.lanball.network.server.dto.MatchEndReason;
import com.unicornstudio.lanball.network.server.dto.Player;
import com.unicornstudio.lanball.network.server.util.ServerRequestBuilder;
import com.unicornstudio.lanball.network.server.util.ServerResponseBuilder;
import com.unicornstudio.lanball.network.server.util.ServerUtils;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.util.WorldUtilService;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class ServerListener extends Listener {

    @Inject
    private ServerDataService serverDataService;

    @Inject
    private WorldUtilService worldUtilService;

    public void connected(Connection connection) {
        if (serverDataService.getPlayerByConnection(connection) == null) {
            serverDataService.addPlayer(connection, new Player(connection.getID()));
        }
    }

    public void disconnected(Connection connection) {
        Optional.ofNullable(serverDataService.getPlayerByConnection(connection))
                .ifPresent(player -> {
                            ServerUtils.propagateData(new PlayerDisconnectRequest(player.getId()), serverDataService.getPlayers(), connection);
                            serverDataService.removePlayer(connection, player);
                        }
                );
    }

    public void received(Connection connection, Object object) {
        if (object instanceof NetworkObject){
            networkObjectReceived(connection, (NetworkObject) object);
        }
    }

    public void idle(Connection connection) {
        if (serverDataService.getTimer() != null) {
            serverDataService.getTimer().tick();
        }
        if (serverDataService.getGameState() == GameState.IN_PROGRESS) {
            if (serverDataService.getTimer().isFinished()) {
                serverDataService.setGameState(GameState.LOBBY);
                if (serverDataService.getTeam1Score() > serverDataService.getTeam2Score()) {
                    ServerUtils.propagateData(
                        ServerRequestBuilder.createMatchEndServerRequest(MatchEndReason.TEAM_1_VICTORY_TIME_OUT),
                        serverDataService.getPlayers(),
                        null);
                } else if (serverDataService.getTeam2Score() > serverDataService.getTeam1Score()) {
                    ServerUtils.propagateData(
                        ServerRequestBuilder.createMatchEndServerRequest(MatchEndReason.TEAM_2_VICTORY_TIME_OUT),
                        serverDataService.getPlayers(),
                        null);
                }
            }
        }
    }

    private void networkObjectReceived(Connection connection, NetworkObject networkObject) {
        Player player = serverDataService.getPlayerByConnection(connection);
        switch (networkObject.getType()) {
            case PLAYER_JOIN:
                if (validateNetworkProtocol(connection, (PlayerJoinClientRequest) networkObject)) {
                    onPlayerJoin(connection, player, (PlayerJoinClientRequest) networkObject);
                }
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
            case GATE_CONTACT:
                onGateContact((GateContactClientRequest) networkObject);
                break;
            case BALL_CONTACT:
                onBallContact();
                break;
            case KEY_PRESS:
                onKeyPress(connection, (PlayerKeyPressClientRequest) networkObject);
                break;
        }
    }

    private void onStartGame(Connection connection) {
        serverDataService.setGameState(GameState.PENDING);
        ServerUtils.propagateData(ServerRequestBuilder.createGameStartServerRequest(), serverDataService.getPlayers(), null);
        setPlayersStartPosition(TeamType.TEAM1);
        setPlayersStartPosition(TeamType.TEAM2);
        setPlayersStartPosition(TeamType.SPECTATORS);
        serverDataService.createNewTimer();
    }

    private void setPlayersStartPosition(TeamType teamType) {
        Map<Connection, Player> players = serverDataService.getPlayersByTeamType(teamType);
        AtomicInteger i = new AtomicInteger();
        i.set(0);
        players.forEach((key, value) -> setPlayerStartPosition(key, value, teamType, i.addAndGet(1)));
    }

    private void setPlayerStartPosition(Connection connection, Player player, TeamType teamType, Integer spawnPointId) {
        ServerUtils.propagateData(ServerRequestBuilder.createPlayerSetStartPositionServerRequest(player, true, teamType, spawnPointId),
                serverDataService.getPlayers(), connection);
        connection.sendUDP(ServerRequestBuilder.createPlayerSetStartPositionServerRequest(player, false, teamType, spawnPointId));
    }

    private void onMapLoad(Connection connection, MapLoadClientRequest request) {
        serverDataService.setMapData(request.getMapData());
        ServerUtils.propagateData(ServerRequestBuilder.createMapLoadServerRequest(request.getMapData()), serverDataService.getPlayers(), null);
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
                serverDataService.getTimeLimitSelectBoxIndex(),
                serverDataService.getMapData()));
    }

    private void onPlayerUpdate(Connection connection, Player player, PlayerUpdateClientRequest object) {
        if (player == null) {
            return;
        }
        player.setPositionX(object.getPositionX());
        player.setPositionY(object.getPositionY());
        player.setVelocityX(object.getVelocityX());
        player.setVelocityY(object.getVelocityY());
        Sender.send(serverDataService.getConnectionsSet(connection),
                PlayerUpdateServerRequest.builder()
                        .id(player.getId())
                        .remote(true)
                        .positionX(player.getPositionX())
                        .positionY(player.getPositionY())
                        .velocityX(player.getVelocityX())
                        .velocityY(player.getVelocityY())
                        .build());
    }

    private void onGetPlayersList(Connection connection) {
        connection.sendUDP(ServerResponseBuilder.createPlayersListNetworkObject(serverDataService.getPlayersSet()));
    }

    private void onPlayerChangeTeam(Player player, PlayerChangeTeamClientRequest object) {
        Player remotePlayer = serverDataService.getPlayerById(object.getId());
        if (remotePlayer != null) {
            remotePlayer.setTeamType(object.getTeamType());
            ServerUtils.propagateData(ServerRequestBuilder.createPlayerChangeTeamServerRequest(remotePlayer), serverDataService.getPlayers(), null);
            if (!serverDataService.getGameState().equals(GameState.LOBBY)) {
                Connection connection = serverDataService.getConnectionByPlayer(player);
                connection.sendUDP(ServerRequestBuilder.createGameStartServerRequest());
                setPlayerStartPosition(connection, player, object.getTeamType(), 1);
            }
        }
    }

    private void onSelectBoxUpdate(SelectBoxUpdateClientRequest object) {
        if (object.getSelectBoxName().equals(SettingsKeys.TIME_LIMIT)) {
            serverDataService.setTimeLimitSelectBoxIndex(object.getSelectedIndex());
        }
        if (object.getSelectBoxName().equals(SettingsKeys.SCORE_LIMIT)) {
            serverDataService.setScoreLimitSelectBoxIndex(object.getSelectedIndex());
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

    private void onGateContact(GateContactClientRequest object) {
        if (object.getTeamType().equals(TeamType.TEAM1)) {
            serverDataService.setGameState(GameState.PENDING);
            serverDataService.setTeam1Score(serverDataService.getTeam1Score() + 1);
            ServerUtils.propagateData(
                    ServerRequestBuilder.createScoreUpdateRequest(TeamType.TEAM1, serverDataService.getTeam1Score()),
                    serverDataService.getPlayers(),
                    null);
        } else if (object.getTeamType().equals(TeamType.TEAM2)) {
            serverDataService.setGameState(GameState.PENDING);
            serverDataService.setTeam2Score(serverDataService.getTeam2Score() + 1);
            ServerUtils.propagateData(
            ServerRequestBuilder.createScoreUpdateRequest(TeamType.TEAM2, serverDataService.getTeam2Score()),
                    serverDataService.getPlayers(),
                    null);
        }
        if (serverDataService.getTeam1Score() >= serverDataService.getScoreLimit()) {
            serverDataService.setGameState(GameState.LOBBY);
            serverDataService.setTimeLimitSelectBoxIndex(serverDataService.getTimeLimitSelectBoxIndex());
            serverDataService.setTeam1Score(0);
            serverDataService.setTeam2Score(0);
            ServerUtils.propagateData(
            ServerRequestBuilder.createMatchEndServerRequest(MatchEndReason.TEAM_1_VICTORY),
                    serverDataService.getPlayers(),
                    null);
        } else if (serverDataService.getTeam2Score() >= serverDataService.getScoreLimit()) {
            serverDataService.setGameState(GameState.LOBBY);
            serverDataService.setTimeLimitSelectBoxIndex(serverDataService.getTimeLimitSelectBoxIndex());
            serverDataService.setTeam1Score(0);
            serverDataService.setTeam2Score(0);
            ServerUtils.propagateData(
            ServerRequestBuilder.createMatchEndServerRequest(MatchEndReason.TEAM_2_VICTORY),
                    serverDataService.getPlayers(),
                    null);
        } else {
            Timer resetPositionsTimer = new Timer("resetPositionsTimer");
            resetPositionsTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            setPlayersStartPosition(TeamType.TEAM1);
                            setPlayersStartPosition(TeamType.TEAM2);
                            setPlayersStartPosition(TeamType.SPECTATORS);
                            resetPositionsTimer.cancel();
                        }
                    },
            4000);
            Timer roundResetTimer = new Timer("roundResetTimer");
            roundResetTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            ServerUtils.propagateData(
                                    ServerRequestBuilder.createRoundResetRequest(object.getTeamType() == TeamType.TEAM1 ? TeamType.TEAM1 : TeamType.TEAM2),
                                    serverDataService.getPlayers(),
                                    null);
                            roundResetTimer.cancel();
                        }
                    },
            4000);
        }
    }

    private void onBallContact() {
        ServerUtils.propagateData(
                ServerRequestBuilder.createBallContactRequest(),
                serverDataService.getPlayers(),
                null);
        serverDataService.setGameState(GameState.IN_PROGRESS);
    }

    private void onKeyPress(Connection connection, PlayerKeyPressClientRequest object) {
        ServerUtils.propagateData(
                ServerRequestBuilder.createPlayerKeyPressServerRequest(object.getPlayerId()),
                serverDataService.getPlayers(),
                connection);
    }

    private boolean validateNetworkProtocol(Connection connection, PlayerJoinClientRequest object) {
        if (object.getNetworkProtocolVersion() != NetworkProtocol.VERSION) {
            connection.close();
            return false;
        }
        return true;
    }

}
