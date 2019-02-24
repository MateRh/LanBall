package com.unicornstudio.lanball.stage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.inject.Singleton;

import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.Screen;
import lombok.Data;

import java.awt.*;
import java.util.stream.Stream;

@Data
@Singleton
public class StageService {

    private Stage stage;

    private Group group;

    public void init() {
        stage = new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
        stage.getCamera().position.set(Screen.getHalfWidth(), Screen.getHalfHeight(), 0);
    }

    public Stage initAndReturn() {
        stage = new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
        stage.getCamera().position.set(Screen.getHalfWidth(), Screen.getHalfHeight(), 0);
        return stage;
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public Camera getCamera() {
        return stage.getCamera();
    }

    public void addActor(Actor actor) {
        group.addActor(actor);
        System.out.println();
        System.out.println();
        Stream.of(stage.getActors().toArray()).forEachOrdered( item-> System.out.println(item.getName() + " " + item.getClass()));
    }

}
