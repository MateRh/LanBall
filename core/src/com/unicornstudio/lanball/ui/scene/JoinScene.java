package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.inject.Inject;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.ui.UserInterfaceUtils;
import com.unicornstudio.lanball.ui.listener.SceneActionListenerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinScene implements Scene {

    private VisWindow window;

    private Map<String, Actor> elements = new HashMap<>();

    @Inject
    private ClientService clientService;

    @Inject
    private SceneActionListenerService sceneActionListenerService;

    public void create(Stage stage) {
        window = UserInterfaceUtils.createWindow("Servers:",852, 480);
        VisTable table = new VisTable();
        table.setFillParent(true);
        List<String> servers = clientService.getServers();
     //   elements.put("ServerScrollPane", UserInterfaceUtils.createVisScrollPane(Align.topLeft, Color.WHITE, servers, getChangeListener()));
        table.add(elements.get("ServerScrollPane")).size(832, 390);
        table.row();
        UserInterfaceUtils.addClearSeparator(table);
        table.row();
        table.add(createBottomSegment()).fillY();
        window.addActor(table);
        stage.addActor(window);
        Gdx.input.setInputProcessor(stage);
        if (servers.size() > 0) {
            updateTextField(servers.get(0));
        }
    }

    @Override
    public void delete() {
        if (window != null) {
            window.remove();
        }
    }

    private VisTable createBottomSegment() {
        createBottomSegmentElements();
        VisTable table = new VisTable(true);
        table.add(new VisLabel("Server IP:"));
        table.add(elements.get("textField"));
        table.addSeparator(true);;
        table.add(elements.get("JoinButton"));
        table.add(elements.get("RefreshButton"));
        table.add(elements.get("CancelButton"));
        return table;
    }

    private void createBottomSegmentElements() {
        elements.put("textField", new VisTextField());
        elements.put("JoinButton", UserInterfaceUtils.createTextButton("Join", getJoinClickListener()));
        elements.put("RefreshButton", UserInterfaceUtils.createTextButton("Refresh", getRefreshClickListener()));
        elements.put("CancelButton",  UserInterfaceUtils.createTextButton("Cancel", sceneActionListenerService.getMainMenuActionListener()));
    }

    private ChangeListener getChangeListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    VisList<String> list = (VisList<String>) actor;
                    updateTextField(list.getSelected());
                } catch (Exception ignored) {}
            }
        };
    }

    private void updateTextField(String text) {
        VisTextField textField = (VisTextField) elements.get("textField");
        textField.setText(text);
    }

    private ClickListener getRefreshClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    VisScrollPane scrollPane = (VisScrollPane) elements.get("ServerScrollPane");
                    VisList list = (VisList) scrollPane.getActor();
                    list.clearItems();
                    list.setItems(clientService.getServers().toArray(new String[0]));
                } catch (Exception ignored) {}
            }
        };
    }

    private ClickListener getJoinClickListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    VisTextField textField = (VisTextField) elements.get("textField");
                    clientService.connect(textField.getText());
                } catch (Exception ignored) {}
            }
        };
    }

}
