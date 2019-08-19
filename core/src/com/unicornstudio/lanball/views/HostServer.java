package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Inject;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.layout.DragPane;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.io.MapChooser;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.server.ServerService;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.prefernces.SettingsType;


import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CRC32;

@Singleton
public class HostServer extends AbstractLmlView {

    @Inject
    private ClientService clientService;

    @Inject
    private MapChooser mapChooser;

    @Inject
    private ServerService serverService;

    @Inject
    private ClientDataService clientDataService;

    @LmlActor("teamOneDragPane")
    private DragPane teamOneDragPane;

    @LmlActor("teamSpectatorsDragPane")
    private DragPane teamSpectatorsDragPane;

    @LmlActor("teamTwoDragPane")
    private DragPane teamTwoDragPane;

    @LmlActor("timeLimitSelectBox")
    private SelectBox<Label> timeLimitSelectBox;

    @LmlActor("scoreLimitSelectBox")
    private SelectBox<Label> scoreLimitSelectBox;

    @LmlActor("pickMapButton")
    private Button pickMapButton;

    @LmlActor("startTheGameButton")
    private Button startTheGameButton;

    @LmlActor("backToMenuTextMenu")
    private TextButton backToMenuTextMenu;

    @LmlActor("mainTable")
    private Table mainTable;

    private Map<TeamType, CRC32> previousPlayersCRC;

    public HostServer() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/HostServer.lml");
    }

    @Override
    public String getViewId() {
        return "hostServer";
    }

    @Override
    public void render() {
        updateTeamDragPanels();
        updateSelectBoxes();
        super.render();
    }

    @LmlAfter
    private void initialize() {
        addListeners();
        scheduleUpdateTimer();
        initializePreviousPlayersCRCs();
        applyPreferences();
        hideServerButtons();
    }

    private void addListeners() {
        backToMenuTextMenu.addListener(createBackToMenuChangeListener());
        if (!clientService.isHost()) {
            return;
        }
        DragPane.DragPaneListener dragPaneListener = createDragPaneListener();
        teamOneDragPane.setListener(dragPaneListener);
        teamTwoDragPane.setListener(dragPaneListener);
        teamSpectatorsDragPane.setListener(dragPaneListener);
        pickMapButton.addListener(createPickupMapListener());
        startTheGameButton.addListener(createStartButtonListener());
        timeLimitSelectBox.addListener(createPreferencesUpdateChangeListener());
        scoreLimitSelectBox.addListener(createPreferencesUpdateChangeListener());
    }

    private void initializePreviousPlayersCRCs() {
        previousPlayersCRC = new HashMap<>();
        previousPlayersCRC.put(TeamType.SPECTATORS, new CRC32());
        previousPlayersCRC.put(TeamType.TEAM1, new CRC32());
        previousPlayersCRC.put(TeamType.TEAM2, new CRC32());
    }

    private void applyPreferences() {
        if (!clientService.isHost()) {
            return;
        }
        Preferences preferences = SettingsType.SERVER.getPreference();
        clientService.sendRequest(ClientRequestBuilder.createSelectBoxUpdateClientRequest(SettingsKeys.TIME_LIMIT, preferences.getInteger(SettingsKeys.TIME_LIMIT)));
        clientService.sendRequest(ClientRequestBuilder.createSelectBoxUpdateClientRequest(SettingsKeys.SCORE_LIMIT, preferences.getInteger(SettingsKeys.SCORE_LIMIT)));
    }

    private void hideServerButtons() {
        if (clientService.isHost()) {
            return;
        }
        startTheGameButton.setDisabled(true);
        pickMapButton.setDisabled(true);
        DragPane.DragPaneListener emptyListener = createEmptyDragPaneListener();
        teamOneDragPane.setListener(emptyListener);
        teamTwoDragPane.setListener(emptyListener);
        teamSpectatorsDragPane.setListener(emptyListener);
        removeListeners(timeLimitSelectBox);
        removeListeners(scoreLimitSelectBox);
    }

    private void updateTeamDragPanels() {
        updateTeamDragPanel(teamOneDragPane, TeamType.TEAM1);
        updateTeamDragPanel(teamTwoDragPane, TeamType.TEAM2);
        updateTeamDragPanel(teamSpectatorsDragPane, TeamType.SPECTATORS);
    }

    private void updateTeamDragPanel(DragPane dragPane, TeamType teamType) {
        CRC32 crc = clientDataService.getPlayersListCrcByTeam(teamType);
        if (!previousPlayersCRC.get(teamType).equals(crc)) {
            dragPane.clear();
            clientDataService.getPlayersByTeam(teamType).forEach(player -> {
                HorizontalGroup group = new HorizontalGroup();
                Label idLabel = new Label(String.valueOf(player.getId()), VisUI.getSkin());
                idLabel.setFontScale(0.0001f);
                group.addActor(idLabel);
                group.addActor(new Label(player.getName(), VisUI.getSkin()));
                dragPane.addActor(group);
            });
            previousPlayersCRC.replace(teamType, crc);
        }
    }

    private void updateSelectBoxes() {
        if (clientDataService.getTimeLimitSelectBoxIndex() != null && timeLimitSelectBox.getSelectedIndex() != clientDataService.getTimeLimitSelectBoxIndex()) {
            timeLimitSelectBox.setSelectedIndex(clientDataService.getTimeLimitSelectBoxIndex());
        }
        if (clientDataService.getScoreLimitSelectBoxIndex() != null && scoreLimitSelectBox.getSelectedIndex() != clientDataService.getScoreLimitSelectBoxIndex()) {
            scoreLimitSelectBox.setSelectedIndex(clientDataService.getScoreLimitSelectBoxIndex());
        }
    }

    private DragPane.DragPaneListener createDragPaneListener() {
        return (dragPane, actor) -> {
            TeamType teamType = getTeamTypeByDragPane(dragPane);
            if (teamType != null) {
                SnapshotArray<Actor> children = ((HorizontalGroup) actor).getChildren();
                clientService.sendRequest(
                        ClientRequestBuilder.createPlayerChangeTeamClientRequest(
                                Integer.valueOf(((Label) children.first()).getText().toString()),
                                teamType
                        )
                );
                return true;
            }
            return false;
        };
    }

    private DragPane.DragPaneListener createEmptyDragPaneListener() {
        return (dragPane, actor) -> false;
    }

    private ChangeListener createPickupMapListener() {
        return new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapChooser.initialize();
                mapChooser.show(getStage());
            }
        };
    }

    private ChangeListener createStartButtonListener() {
        return new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (clientService.isHost()) {
                    clientService.sendRequest(ClientRequestBuilder.createGameStartClientRequest());
                }
            }
        };
    }

    private ChangeListener createBackToMenuChangeListener() {
        return new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (clientService.isHost()) {
                    serverService.stop();
                } else {
                    clientService.disconnect();
                }
                ((LanBallGame) Gdx.app.getApplicationListener()).setView(Menu.class);
            }
        };
    }

    private ChangeListener createPreferencesUpdateChangeListener() {
        return new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Preferences preferences = SettingsType.SERVER.getPreference();
                if (actor.equals(timeLimitSelectBox)) {
                    int selectedIndex = ((SelectBox) actor).getSelectedIndex();
                    updatePreference(preferences, SettingsKeys.TIME_LIMIT, selectedIndex);
                    clientService.sendRequest(ClientRequestBuilder.createSelectBoxUpdateClientRequest(SettingsKeys.TIME_LIMIT, selectedIndex));
                } else if (actor.equals(scoreLimitSelectBox)) {
                    int selectedIndex = ((SelectBox) actor).getSelectedIndex();
                    updatePreference(preferences, SettingsKeys.SCORE_LIMIT, selectedIndex);
                    clientService.sendRequest(ClientRequestBuilder.createSelectBoxUpdateClientRequest(SettingsKeys.SCORE_LIMIT, selectedIndex));
                }
            }
        };
    }

    private void updatePreference(Preferences preferences, String key, Integer value) {
        preferences.putInteger(key, value);
        preferences.flush();
    }

    private TeamType getTeamTypeByDragPane(DragPane dragPane) {
        if (dragPane == teamOneDragPane) {
            return TeamType.TEAM1;
        } else if (dragPane == teamTwoDragPane) {
            return TeamType.TEAM2;
        } else if (dragPane == teamSpectatorsDragPane) {
            return TeamType.SPECTATORS;
        }
        return null;
    }

    private void scheduleUpdateTimer() {
        Timer timer = new Timer("UpdateTimer");
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        updateTeamDragPanels();
                        updateSelectBoxes();
                    }
                }, 500, 500);
    }

    private void removeListeners(Actor actor) {
        actor.getListeners().forEach(
                actor::removeListener
        );
    }

}
