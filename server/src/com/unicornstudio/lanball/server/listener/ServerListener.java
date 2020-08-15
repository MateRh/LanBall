package com.unicornstudio.lanball.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.network.model.enumeration.MatchEndReason;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkProtocol;
import com.unicornstudio.lanball.network.model.protocol.PlayerDisconnect;
import com.unicornstudio.lanball.network.model.protocol.client.GateContactClient;
import com.unicornstudio.lanball.network.model.protocol.client.MapLoadClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerChangeTeamClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerJoinClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerKeyPressClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerKickBallClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerUpdateClient;
import com.unicornstudio.lanball.network.model.protocol.client.SelectBoxUpdateClient;
import com.unicornstudio.lanball.network.model.protocol.common.BallUpdate;
import com.unicornstudio.lanball.network.model.protocol.server.PlayerUpdateServer;
import com.unicornstudio.lanball.server.builder.ServerRequestBuilder;
import com.unicornstudio.lanball.server.builder.ServerResponseBuilder;
import com.unicornstudio.lanball.server.service.SenderService;
import com.unicornstudio.lanball.server.service.ServerDataService;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class ServerListener extends Listener {

    private static final String TIME_LIMIT = "time_limit";
    private static final String SCORE_LIMIT = "score_limit";

    private final ServerDataService serverDataService;

    @Inject
    public ServerListener(ServerDataService serverDataService) {
        this.serverDataService = serverDataService;
    }

    public void connected(Connection connection) {
        if (serverDataService.getPlayerByConnection(connection) == null) {
            serverDataService.addPlayer(connection, new Player(connection.getID()));
        }
    }

    public void disconnected(Connection connection) {
        Optional.ofNullable(serverDataService.getPlayerByConnection(connection))
                .ifPresent(player -> {
                            propagateData(new PlayerDisconnect(player.getId()), serverDataService.getPlayers(), connection);
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
                    propagateData(
                            ServerRequestBuilder.createMatchEndServer(MatchEndReason.TEAM_1_VICTORY_TIME_OUT),
                            serverDataService.getPlayers(),
                            null);
                } else if (serverDataService.getTeam2Score() > serverDataService.getTeam1Score()) {
                    propagateData(
                            ServerRequestBuilder.createMatchEndServer(MatchEndReason.TEAM_2_VICTORY_TIME_OUT),
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
                if (validateNetworkProtocol(connection, (PlayerJoinClient) networkObject)) {
                    onPlayerJoin(connection, player, (PlayerJoinClient) networkObject);
                }
                break;
            case PLAYER_UPDATE:
                onPlayerUpdate(connection, player, (PlayerUpdateClient) networkObject);
                break;
            case GET_PLAYERS_LIST:
                onGetPlayersList(connection);
                break;
            case PLAYER_CHANGE_TEAM:
                onPlayerChangeTeam(player, (PlayerChangeTeamClient) networkObject);
                break;
            case BALL_UPDATE:
                onBallUpdate(connection, (BallUpdate) networkObject);
                break;
            case MAP_LOAD:
                onMapLoad(connection, (MapLoadClient) networkObject);
                break;
            case START_GAME:
                onStartGame(connection);
                break;
            case SELECT_BOX_UPDATE:
                onSelectBoxUpdate((SelectBoxUpdateClient) networkObject);
                break;
            case BALL_KICK:
                onBallKick((PlayerKickBallClient) networkObject);
                break;
            case GATE_CONTACT:
                onGateContact((GateContactClient) networkObject);
                break;
            case BALL_CONTACT:
                onBallContact();
                break;
            case KEY_PRESS:
                onKeyPress(connection, (PlayerKeyPressClient) networkObject);
                break;
        }
    }

    private void onStartGame(Connection connection) {
        serverDataService.setGameState(GameState.PENDING);
        propagateData(ServerRequestBuilder.createGameStartServer(), serverDataService.getPlayers(), null);
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
        propagateData(ServerRequestBuilder.createPlayerSetStartPositionServer(player, true, teamType, spawnPointId),
                serverDataService.getPlayers(), connection);
        connection.sendUDP(ServerRequestBuilder.createPlayerSetStartPositionServer(player, false, teamType, spawnPointId));
    }

    private void onMapLoad(Connection connection, MapLoadClient request) {
        serverDataService.setMapData(request.getMapData());
        propagateData(ServerRequestBuilder.createMapLoadServer(request.getMapData()), serverDataService.getPlayers(), null);
    }

    private void onBallUpdate(Connection connection, BallUpdate request) {
        serverDataService.updateBall(request.getPositionX(), request.getPositionY(), request.getVelocityX(), request.getVelocityY());
        propagateData(request, serverDataService.getPlayers(), connection);
    }

    private void onPlayerJoin(Connection connection, Player player, PlayerJoinClient object) {
        if (player == null) {
            return;
        }
        player.setName(object.getName());
        propagateData(ServerRequestBuilder.createRemotePlayer(player, false), serverDataService.getPlayers(), connection);
        connection.sendUDP(ServerRequestBuilder.createServerStateServer(
                serverDataService.getPlayersSet(),
                player,
                serverDataService.getGameState(),
                serverDataService.getScoreLimitSelectBoxIndex(),
                serverDataService.getTimeLimitSelectBoxIndex(),
                serverDataService.getMapData()));
    }

    private void onPlayerUpdate(Connection connection, Player player, PlayerUpdateClient object) {
        if (player == null) {
            return;
        }
        player.setPositionX(object.getPositionX());
        player.setPositionY(object.getPositionY());
        player.setVelocityX(object.getVelocityX());
        player.setVelocityY(object.getVelocityY());
        SenderService.send(serverDataService.getConnectionsSet(connection),
                PlayerUpdateServer.builder()
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

    private void onPlayerChangeTeam(Player player, PlayerChangeTeamClient object) {
        Player remotePlayer = serverDataService.getPlayerById(object.getId());
        if (remotePlayer != null) {
            remotePlayer.setTeamType(object.getTeamType());
            propagateData(ServerRequestBuilder.createPlayerChangeTeamServer(remotePlayer), serverDataService.getPlayers(), null);
            if (!serverDataService.getGameState().equals(GameState.LOBBY)) {
                Connection connection = serverDataService.getConnectionByPlayer(player);
                connection.sendUDP(ServerRequestBuilder.createGameStartServer());
                setPlayerStartPosition(connection, player, object.getTeamType(), 1);
            }
        }
    }

    private void onSelectBoxUpdate(SelectBoxUpdateClient object) {
        if (object.getSelectBoxName().equals(TIME_LIMIT)) {
            serverDataService.setTimeLimitSelectBoxIndex(object.getSelectedIndex());
        }
        if (object.getSelectBoxName().equals(SCORE_LIMIT)) {
            serverDataService.setScoreLimitSelectBoxIndex(object.getSelectedIndex());
        }
        propagateData(
                ServerRequestBuilder.createSelectBoxUpdateServer(object.getSelectBoxName(), object.getSelectedIndex()),
                serverDataService.getPlayers(),
                null);
    }

    private void onBallKick(PlayerKickBallClient object) {
        propagateData(
                ServerRequestBuilder.createPlayerKickBallServer(object.getPlayerId(), object.getForceX(), object.getForceY(), object.getPointX(), object.getPointY()),
                serverDataService.getPlayers(),
                null
        );
    }

    private void onGateContact(GateContactClient object) {
        if (object.getTeamType().equals(TeamType.TEAM1)) {
            serverDataService.setGameState(GameState.PENDING);
            serverDataService.setTeam1Score(serverDataService.getTeam1Score() + 1);
            propagateData(
                    ServerRequestBuilder.createScoreUpdate(TeamType.TEAM1, serverDataService.getTeam1Score()),
                    serverDataService.getPlayers(),
                    null);
        } else if (object.getTeamType().equals(TeamType.TEAM2)) {
            serverDataService.setGameState(GameState.PENDING);
            serverDataService.setTeam2Score(serverDataService.getTeam2Score() + 1);
            propagateData(
            ServerRequestBuilder.createScoreUpdate(TeamType.TEAM2, serverDataService.getTeam2Score()),
                    serverDataService.getPlayers(),
                    null);
        }
        if (serverDataService.getTeam1Score() >= serverDataService.getScoreLimit()) {
            serverDataService.setGameState(GameState.LOBBY);
            serverDataService.setTimeLimitSelectBoxIndex(serverDataService.getTimeLimitSelectBoxIndex());
            serverDataService.setTeam1Score(0);
            serverDataService.setTeam2Score(0);
            propagateData(
            ServerRequestBuilder.createMatchEndServer(MatchEndReason.TEAM_1_VICTORY),
                    serverDataService.getPlayers(),
                    null);
        } else if (serverDataService.getTeam2Score() >= serverDataService.getScoreLimit()) {
            serverDataService.setGameState(GameState.LOBBY);
            serverDataService.setTimeLimitSelectBoxIndex(serverDataService.getTimeLimitSelectBoxIndex());
            serverDataService.setTeam1Score(0);
            serverDataService.setTeam2Score(0);
            propagateData(
            ServerRequestBuilder.createMatchEndServer(MatchEndReason.TEAM_2_VICTORY),
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
                            propagateData(
                                    ServerRequestBuilder.createRoundReset(object.getTeamType() == TeamType.TEAM1 ? TeamType.TEAM1 : TeamType.TEAM2),
                                    serverDataService.getPlayers(),
                                    null);
                            roundResetTimer.cancel();
                        }
                    },
            4000);
        }
    }

    private void onBallContact() {
        propagateData(
                ServerRequestBuilder.createBallContact(),
                serverDataService.getPlayers(),
                null);
        serverDataService.setGameState(GameState.IN_PROGRESS);
    }

    private void onKeyPress(Connection connection, PlayerKeyPressClient object) {
        propagateData(
                ServerRequestBuilder.createPlayerKeyPressServer(object.getPlayerId()),
                serverDataService.getPlayers(),
                connection);
    }

    private boolean validateNetworkProtocol(Connection connection, PlayerJoinClient object) {
        if (object.getNetworkProtocolVersion() != NetworkProtocol.VERSION) {
            connection.close();
            return false;
        }
        return true;
    }

    public void propagateData(NetworkObject networkObject, Map<Connection, Player> players, Connection initializerConnection) {
        players.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(initializerConnection))
                .map(Map.Entry::getKey)
                .forEach(connection -> connection.sendUDP(networkObject));
    }

}
