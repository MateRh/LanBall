package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.ui.UserInterfaceUtils;

public class JoinScene implements Scene {

    private VisWindow window;

    public void create(Stage stage) {
        window = UserInterfaceUtils.createWindow("Servers:",852, 480);
        VisTable table = new VisTable();
        table.setFillParent(true);
        table.add(UserInterfaceUtils.createVisScrollPane(Align.topLeft, Color.WHITE, new String[1])).size(832, 390);
        table.row();
        UserInterfaceUtils.addClearSeparator(table);
        table.row();
        table.add(createBottomSegment()).fillY();
        window.addActor(table);
        stage.addActor(window);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void delete() {
        if (window != null) {
            window.remove();
        }
    }

    private VisTable createBottomSegment() {
        VisTable table = new VisTable(true);
        table.add(new VisLabel("Server IP:"));
        table.add(new VisTextField());
        table.addSeparator(true);;
        table.add(new VisTextButton("Join"));
        table.add(new VisTextButton("Refresh"));
        table.add(new VisTextButton("Cancel"));
        return table;
    }

}
