package com.unicornstudio.lanball.network.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.map.MapService;
import com.unicornstudio.lanball.map.settings.Team;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.PlayerDtoMapper;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.RemotePlayerServerRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ServerStateServerRequest;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.views.Game;

@Singleton
public class ClientListener extends Listener {

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private MapService mapService;

    @Inject
    private ClientDataService clientDataService;

    public void received(Connection connection, Object object) {
        if (object instanceof NetworkObject) {
            networkObjectReceived(connection, (NetworkObject) object);
        }
    }

    private void networkObjectReceived(Connection connection, NetworkObject object) {
        switch (object.getType()) {
            case REMOTE_PLAYER:
                onRemotePlayer((RemotePlayerServerRequest) object);
                break;
            case PLAYER_UPDATE:
                onPlayerUpdate((PlayerUpdateServerRequest) object);
                break;
            case SERVER_STATE:
                onServerState((ServerStateServerRequest) object);
                break;
            case BALL_UPDATE:
                onBallUpdate((BallUpdateServerRequest) object);
                break;
            case MAP_LOAD:
                onMapUpdate((MapLoadServerRequest) object);
                break;
            case START_GAME:
                onStartGame();
                break;
            case PLAYER_CHANGE_TEAM:
                onPlayerChangeTeam((PlayerChangeTeamServerRequest) object);
                break;
            case SELECT_BOX_UPDATE:
                onSelectBoxUpdate((SelectBoxUpdateServerRequest) object);
                break;
            case BALL_KICK:
                onBallKick((PlayerKickBallServerRequest) object);
                break;
        }
    }

    private void onStartGame() {
        Gdx.app.postRunnable(() -> {
            clientDataService.setGameState(GameState.IN_PROGRESS);
            ((LanBallGame) Gdx.app.getApplicationListener()).setView(Game.class);
            mapService.initialize(clientDataService.getPlayers());
        });
    }

    private void onMapUpdate(MapLoadServerRequest request) {
        Gdx.app.postRunnable(() -> mapService.loadMap(request.getMapData()));
    }

    private void onBallUpdate(BallUpdateServerRequest request) {
        Gdx.app.postRunnable(() -> entitiesService.updateBall(
                new Vector2(request.getPositionX(), request.getPositionY()),
                new Vector2(request.getVelocityX(), request.getVelocityY())
        ));
    }

    private void onRemotePlayer(RemotePlayerServerRequest object) {
        createContestant(object);
    }

    private void onPlayerUpdate(PlayerUpdateServerRequest object) {
        Gdx.app.postRunnable(() -> entitiesService.updateContestantData(
                object.getId(),
                new Vector2(object.getPositionX(), object.getPositionY()),
                new Vector2(object.getVelocityX(), object.getVelocityY())
        ));
    }

    private void onServerState(ServerStateServerRequest object) {
        clientDataService.setGameState(object.getGameState());
        object.getPlayers().forEach(this::createContestant);
        if (object.getScoreLimitSelectBoxIndex() != null) {
            clientDataService.setScoreLimitSelectBoxIndex(object.getScoreLimitSelectBoxIndex());
        }
        if (object.getTimeLimitSelectBoxIndex() != null) {
            clientDataService.setTimeLimitSelectBoxIndex(object.getTimeLimitSelectBoxIndex());
        }
    }

    private void onPlayerChangeTeam(PlayerChangeTeamServerRequest object) {
        PlayerDto player = clientDataService.getPlayerById(object.getId());
        if (player != null) {
            player.setTeamType(object.getTeamType());
            clientDataService.changePlayerTeam(player, player.getTeamType(), object.getTeamType());
        }
    }

    private void onSelectBoxUpdate(SelectBoxUpdateServerRequest object) {
        if (object.getSelectBoxName().equals(SettingsKeys.TIME_LIMIT)) {
            clientDataService.setTimeLimitSelectBoxIndex(object.getSelectedIndex());
        }
        if (object.getSelectBoxName().equals(SettingsKeys.SCORE_LIMIT)) {
            clientDataService.setScoreLimitSelectBoxIndex(object.getSelectedIndex());
        }
    }

    private void onBallKick(PlayerKickBallServerRequest object) {
        Body ballBody = getBallBody();
        if (ballBody != null) {
            ballBody.applyLinearImpulse(new Vector2(object.getForceX(), object.getForceY()), new Vector2(object.getPointX(), object.getPointY()), false);
        }
    }

    private void createContestant(RemotePlayerServerRequest request) {
        clientDataService.addPlayer(PlayerDtoMapper.createPlayer(request), request.getTeamType());
        if (!clientDataService.getGameState().equals(GameState.IN_LOBBY) && !request.isRemotePlayer()) {
            Gdx.app.postRunnable(() -> entitiesService.createContestant(request.getId(), request.getName(), getTeamByType(request.getTeamType())));
        }
    }

    private Team getTeamByType(TeamType teamType) {
        return mapService.getMap().getSettings().getTeams().get(teamType.getType());
    }

    private Body getBallBody() {
        Entity entity = entitiesService.getEntity("ball");
        if (entity == null) {
            return null;
        }
        return entity.getPhysicsEntity().getBody();
    }

}
