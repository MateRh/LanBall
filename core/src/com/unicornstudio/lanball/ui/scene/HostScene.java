package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.google.inject.Inject;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.io.MapChooser;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.server.ServerService;
import com.unicornstudio.lanball.network.common.Ports;
import com.unicornstudio.lanball.network.server.Player;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.ui.SceneElementsContainer;
import com.unicornstudio.lanball.ui.UserInterfaceUtils;
import com.unicornstudio.lanball.ui.listener.MainMenuActionListener;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class HostScene implements Scene {

    @Inject
    private SceneService sceneService;

    @Inject
    private StageService stageService;

    @Inject
    private ServerService serverService;

    @Inject
    private MainMenuActionListener mainMenuActionListener;

    @Inject
    private MapChooser mapChooser;

    @Inject
    private SceneElementsContainer container;

    private VisWindow window;

    private Timer listUpdateTimer;

    public void create(Stage stage) {
        listUpdateTimer = new Timer("listUpdateTimer", true);
        window = UserInterfaceUtils.createWindow("Host game:",852, 480);
        VisTable table = new VisTable(true);
        table.setFillParent(true);
        table.add(new VisTextButton("Red Team"));
        table.add(new VisTextButton("Players"));
        table.add(new VisTextButton("Blue Team")).row();
        table.add(
                container.add("redTeamPane",
                        UserInterfaceUtils.createVisScrollPane(Align.center, new Color(1, 0f, 0f, 0.1f),
                                Collections.emptyList(), Event::getBubbles)
                )
        ).size(200, 280);
        table.add(
                container.add("spectatorsPane",
                        UserInterfaceUtils.createVisScrollPane(Align.center, new Color(1, 1, 1, 0.1f),
                                Collections.emptyList(), Event::getBubbles)
                )
        ).size(300, 280);
        table.add(
                container.add("blueTeamPane",
                        UserInterfaceUtils.createVisScrollPane(Align.center, new Color(0.0f, 0.0f, 1, 0.1f),
                                Collections.emptyList(), Event::getBubbles)
                )
        ).size(200, 280);
        table.row();
        table.add(createButtonActionsTable()).size(200, 100);
        table.add(createSettingsTable());
        table.add(createStartActionTable()).row();
        window.addActor(table);
        stage.addActor(window);
        serverService.start(Ports.getList().get(0));
        listUpdateTimer.scheduleAtFixedRate(getUpdatePanesTask(), 50, 50);
        //window.setScale(0);
        mapChooser.initialize();
    }

    @Override
    public void delete() {
        if (window != null) {
            window.remove();
        }
        serverService.stop();
        listUpdateTimer.cancel();
    }

    private TimerTask getUpdatePanesTask() {
        return new TimerTask() {
            @Override
            public void run() {
                serverService.getData().getPlayers().forEach(player -> updatePanes(player));
            }
        };
    }

    private void updatePanes(Player player) {
        VisScrollPane pane = getPaneByTeamType(player.getTeamType());
        if (pane != null) {
            VisList list = (VisList) pane.getActor();
            list.clearItems();
            Array<String> array = list.getItems();
            array.add(player.getName());
            list.setItems(array);
        }
    }

    private VisScrollPane getPaneByTeamType(TeamType teamType) {
        VisScrollPane pane;
        switch (teamType) {
            case SPECTATORS:
                pane = (VisScrollPane) container.get("spectatorsPane");
                break;
            case TEAM1:
                pane = (VisScrollPane) container.get("redTeamPane");
                break;
            case TEAM2:
                pane = (VisScrollPane) container.get("blueTeamPane");
                break;
            default:
                pane = null;
        }
        return pane;
    }

    private VisTable createButtonActionsTable() {
        VisTable table = new VisTable(true);
        table.add(new VisTextButton("Auto"));
        table.add(new VisTextButton("Rand")).row();
        table.add(new VisTextButton("Lock"));
        table.add(new VisTextButton("Reset")).row();
        return table;
    }

    private VisTable createSettingsTable() {
        VisTable table = new VisTable(true);
        table.add(new VisLabel("Time limit:"));
        table.add(new VisTextField("05:00"));
        table.add(createClearSeparator());
        table.row();
        table.add(new VisLabel("Score limit:"));
        table.add(new VisTextField("5"));
        table.add(createClearSeparator());
        table.row();
        table.add(new VisLabel("Stadium:"));
        table.add(new VisLabel("Classic"));
        table.add(UserInterfaceUtils.createTextButton("Pick", getMapChooserClickListener()));
        table.row();
        return table;
    }

    private Separator createClearSeparator() {
        Separator separator = new Separator();
        separator.setColor(Color.CLEAR);
        return separator;
    }

    private VisTable createStartActionTable() {
        VisTable table = new VisTable(true);
        VisTextButton visTextButton = new VisTextButton("Start game");
        visTextButton.setColor(Color.GREEN);
        visTextButton.setRound(true);
        table.add(visTextButton).row();
        table.add(UserInterfaceUtils.createTextButton("Back", mainMenuActionListener)).row();
        return table;
    }

    private ClickListener getMapChooserClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    mapChooser.show(stageService.getStage());
                } catch (Exception ignored) {}
            }
        };
    }

}
