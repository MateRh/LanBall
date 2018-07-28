package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.LanBallGameModule;
import com.unicornstudio.lanball.ui.UserInterfaceUtils;
import com.unicornstudio.lanball.ui.listener.HostActionListener;
import com.unicornstudio.lanball.ui.listener.JoinActionListener;
import com.unicornstudio.lanball.ui.listener.QuitActionListener;

public class MainMenu implements Scene {

    private VisWindow window;

    public MainMenu() {
    }

    public void create(Stage stage) {
        window = UserInterfaceUtils.createWindow("LanBall", 300, 400);
        VerticalGroup verticalGroup = new VerticalGroup();

        VisImage image = new VisImage(new Texture("logo.png"));
        verticalGroup.addActor(image);
        verticalGroup.addActor(createTextButton("JOIN", new JoinActionListener()));
        verticalGroup.addActor(createTextButton("HOST", new HostActionListener()));
        verticalGroup.addActor(createTextButton("SETTINGS", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        }));
        verticalGroup.addActor(createTextButton("HELP", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        }));
        verticalGroup.addActor(createTextButton("QUIT", new QuitActionListener()));
        verticalGroup.center();
        verticalGroup.space(10f);
        verticalGroup.setPosition(50, 0);
        verticalGroup.setWidth(200);
        verticalGroup.setHeight(400);
        window.addActor(verticalGroup);
        stage.addActor(window);
        Gdx.input.setInputProcessor(stage);
    }

    private VisTextButton createTextButton(String text, ClickListener listener) {
        VisTextButton button = new VisTextButton(text);
        button.addListener(listener);
        return button;
    }

    @Override
    public void delete() {
        if (window != null) {
            window.remove();
        }
    }

}
