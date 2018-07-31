package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.network.GameServer;
import com.unicornstudio.lanball.ui.UserInterfaceUtils;

public class HostScene implements Scene {

    private final SceneService sceneService;

    private VisWindow window;

    public HostScene(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    public void create(Stage stage) {
        window = UserInterfaceUtils.createWindow("Host game:",852, 480);
        VisTable table = new VisTable(true);
        table.setFillParent(true);
        table.add(new VisTextButton("Red Team"));
        table.add(new VisTextButton("Players"));
        table.add(new VisTextButton("Blue Team")).row();
        table.add(UserInterfaceUtils.createVisScrollPane(Align.center, new Color(1, 0f, 0f, 0.1f), new String[1])).size(200, 280);
        table.add(UserInterfaceUtils.createVisScrollPane(Align.center, new Color(1, 1, 1, 0.1f), new String[1])).size(300, 280);
        table.add(UserInterfaceUtils.createVisScrollPane(Align.center, new Color(0.0f, 0.0f, 1, 0.1f), new String[1])).size(200, 280);
        table.row();
        table.add(createButtonActionsTable()).size(200, 100);
        table.add(createSettingsTable());
        table.add(createStartActionTable()).row();
        window.addActor(table);
        stage.addActor(window);
        new GameServer().start();
        delete();
    }

    @Override
    public void delete() {
        if (window != null) {
            window.remove();
        }
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
        table.add(new VisTextButton("Pick"));
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
        table.add(new VisTextButton("Back")).row();
        return table;
    }

}
