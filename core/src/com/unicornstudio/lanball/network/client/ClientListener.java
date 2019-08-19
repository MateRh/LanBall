package com.unicornstudio.lanball.network.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.unicornstudio.lanball.builder.FontBuilder;
import com.unicornstudio.lanball.network.protocol.request.PlayerDisconnectRequest;
import com.unicornstudio.lanball.network.protocol.request.RoundResetRequest;
import com.unicornstudio.lanball.service.BallService;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.audio.SoundService;
import com.unicornstudio.lanball.audio.SoundType;
import com.unicornstudio.lanball.model.map.MapService;
import com.unicornstudio.lanball.model.map.settings.Team;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.PlayerDtoMapper;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MatchEndServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerSetStartPositionServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.RemotePlayerServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ScoreUpdateRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ServerStateServerRequest;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.service.StageService;
import com.unicornstudio.lanball.service.WorldService;
import com.unicornstudio.lanball.util.CompressionUtil;
import com.unicornstudio.lanball.util.WorldUtilService;
import com.unicornstudio.lanball.views.Game;
import com.unicornstudio.lanball.views.HostServer;

import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class ClientListener extends Listener {

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private MapService mapService;

    @Inject
    private ClientDataService clientDataService;

    @Inject
    private SoundService soundService;

    @Inject
    private WorldUtilService worldUtilService;

    @Inject
    private WorldService worldService;

    @Inject
    private BallService ballService;

    @Inject
    private StageService stageService;

    public void received(Connection connection, Object object) {
        if (object instanceof NetworkObject) {
            networkObjectReceived(connection, (NetworkObject) object);
        }
    }

    public void disconnected(Connection connection) {
        clientDataService.clear();
    }

    public void idle(Connection connection) {
        if (clientDataService.getTimer() != null) {
            clientDataService.getTimer().tick();
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
            case PLAYER_DISCONNECT:
                onPlayerDisconnect((PlayerDisconnectRequest) object);
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
            case MATCH_END:
                onMatchEnd((MatchEndServerRequest) object);
                break;
            case SCORE_UPDATE:
                onScoreUpdate((ScoreUpdateRequest) object);
                break;
            case START_POSITION:
                onSetStartPosition((PlayerSetStartPositionServerRequest) object);
                break;
            case ROUND_RESET:
                onRoundReset((RoundResetRequest) object);
                break;
            case BALL_CONTACT:
                onBallContact();
                break;
        }
    }

    private void onRoundReset(RoundResetRequest request) {
        worldService.updateInitialRoundBoundsFilter(request.getStartingTeam());
        worldService.setInitialRoundBoundsActive(true);
        ballService.reset();
        ballService.setListenerStatus(!request.getStartingTeam().equals(clientDataService.getRemotePlayer().getTeamType()));
    }

    private void onStartGame() {
        clientDataService.createNewTimer();
        Gdx.app.postRunnable(() -> {
            clientDataService.setGameState(GameState.PENDING);
            ((LanBallGame) Gdx.app.getApplicationListener()).setView(Game.class);
            mapService.initialize(clientDataService.getPlayers());
            ballService.addBallListener();
            if (clientDataService.getRemotePlayer().getTeamType().equals(TeamType.TEAM1)) {
                ballService.setListenerStatus(true);
            }
            worldService.updateInitialRoundBoundsFilter(TeamType.TEAM2);
        });
    }

    private void onMapUpdate(MapLoadServerRequest request) {
        Gdx.app.postRunnable(() ->
                mapService.loadMap(CompressionUtil.decompress(request.getMapData())));
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
        Gdx.app.postRunnable(() -> {
            if (object.isRemote()) {
                entitiesService.updateContestantData(
                        object.getId(),
                        new Vector2(object.getPositionX(), object.getPositionY()),
                        new Vector2(object.getVelocityX(), object.getVelocityY()));
            } else {
                entitiesService.updatePlayerData(
                        new Vector2(object.getPositionX(), object.getPositionY()),
                        new Vector2(object.getVelocityX(), object.getVelocityY()));
            }
        });
    }

    private void onPlayerDisconnect(PlayerDisconnectRequest request) {
        PlayerDto player = clientDataService.getPlayerById(request.getId());
        if (player != null) {
            Gdx.app.postRunnable(() -> {
                entitiesService.removeContestant(player.getId());
                soundService.playSound(SoundType.DISCONNECT);
            });
            clientDataService.removePlayer(player, player.getTeamType());
        }
    }

    private void onSetStartPosition(PlayerSetStartPositionServerRequest object) {
        Vector2 position = worldUtilService.calcPosition(object.getTeamType(), object.getPositionId());
        System.out.println(position);
        Gdx.app.postRunnable(() -> {
            if (object.isRemote()) {
                entitiesService.updateContestantData(object.getId(), position, Vector2.Zero);
            } else {
                entitiesService.updatePlayerData(position, Vector2.Zero);
            }
        });
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
        if (object.getMapData() != null) {
            Gdx.app.postRunnable(() ->
                    mapService.loadMap(CompressionUtil.decompress(object.getMapData())));
        }
    }

    private void onPlayerChangeTeam(PlayerChangeTeamServerRequest object) {
        PlayerDto player = clientDataService.getPlayerById(object.getId());
        if (player != null) {
            clientDataService.changePlayerTeam(player, player.getTeamType(), object.getTeamType());
            player.setTeamType(object.getTeamType());
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
            soundService.playSound(SoundType.KICK);
            ballBody.applyLinearImpulse(new Vector2(object.getForceX(), object.getForceY()), new Vector2(object.getPointX(), object.getPointY()), false);
        }
    }

    private void onMatchEnd(MatchEndServerRequest object) {
        clientDataService.setGameState(GameState.PENDING);
        Timer timer = new Timer("matchEnd");
        Timer timer_backToMenu = new Timer("matchEnd_backToMenu");
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        switch (object.getEndReason()) {
                            case CANCELED:
                                Gdx.app.postRunnable(() -> showMatchEndModal("Match canceled.", ""));
                                break;
                            case TEAM_1_VICTORY:
                                Gdx.app.postRunnable(() -> showMatchEndModal("Team 1 won the match.", ""));
                                break;
                            case TEAM_2_VICTORY:
                                Gdx.app.postRunnable(() -> showMatchEndModal("Team 2 won the match.", ""));
                                break;
                            case TEAM_1_VICTORY_TIME_OUT:
                                Gdx.app.postRunnable(() -> showMatchEndModal("Team 1 won the match.", "Time's up."));
                                break;
                            case TEAM_2_VICTORY_TIME_OUT:
                                Gdx.app.postRunnable(() -> showMatchEndModal("Team 2 won the match.", "Time's up."));
                                break;
                        }
                    }
                }, 4500
        );
        timer_backToMenu.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("matchEnd_backToMenu");
                        clientDataService.setGameState(GameState.LOBBY);
                        clientDataService.setTimeLimitSelectBoxIndex(clientDataService.getTimeLimitSelectBoxIndex());
                        clientDataService.setTeam1Score(0);
                        clientDataService.setTeam2Score(0);
                        Gdx.app.postRunnable(() -> {
                            mapService.dispose();
                        });
                        ((LanBallGame) Gdx.app.getApplicationListener()).setView(HostServer.class);
                    }
                }, 9500
        );
    }

    private void onScoreUpdate(ScoreUpdateRequest object) {
        if (object.getTeamType().equals(TeamType.TEAM1)) {
            clientDataService.setGameState(GameState.PENDING);
            clientDataService.setTeam1Score(object.getScore());
            Gdx.app.postRunnable(() -> showMatchEndModal("Team 1 scored!", ""));
        } else if (object.getTeamType().equals(TeamType.TEAM2)) {
            clientDataService.setGameState(GameState.PENDING);
            clientDataService.setTeam2Score(object.getScore());
            Gdx.app.postRunnable(() -> showMatchEndModal("Team 2 scored!", ""));
        }
    }

    private void onBallContact() {
        ballService.setListenerStatus(false);
        worldService.setInitialRoundBoundsActive(false);
        clientDataService.setGameState(GameState.IN_PROGRESS);
    }

    private void createContestant(RemotePlayerServerRequest request) {
        clientDataService.addPlayer(PlayerDtoMapper.createPlayer(request), request.getTeamType());
        if (!clientDataService.getGameState().equals(GameState.LOBBY) && !request.isRemotePlayer()) {
            Gdx.app.postRunnable(() -> entitiesService.createContestant(request.getId(), request.getName(), getTeamByType(request.getTeamType()), request.getTeamType()));
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

    private void showMatchEndModal(String title, String subTitle) {
        Dialog dialog = new Dialog("", VisUI.getSkin());
        dialog.setBackground("transparent");
        dialog.setModal(true);
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10f);
        VisLabel titleLabel = new VisLabel();
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleLabel.getStyle());
        titleLabelStyle.font = new FontBuilder()
                .name("CuteFont-Regular")
                .size(64)
                .color(Color.RED)
                .borderWidth(2f)
                .build();
        titleLabel.setText(title);
        titleLabel.setStyle(titleLabelStyle);
        VisLabel subTitleLabel = new VisLabel();
        Label.LabelStyle subTitleLabelStyle = new Label.LabelStyle(titleLabel.getStyle());
        subTitleLabelStyle.font =  new FontBuilder()
                .name("CuteFont-Regular")
                .size(32)
                .color(Color.RED)
                .borderWidth(1f)
                .build();
        subTitleLabel.setText(subTitle);
        subTitleLabel.setStyle(subTitleLabelStyle);
        verticalGroup.addActor(titleLabel);
        verticalGroup.addActor(subTitleLabel);
        dialog.add(verticalGroup);
        dialog.show(LanBallGame.getStage());
        Timer timer = new Timer("showMatchEndModal_" + title);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        dialog.remove();
                        timer.purge();
                    }
                }, 4000
        );
    }

}
