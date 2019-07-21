package com.unicornstudio.lanball.service;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.inject.Singleton;

import com.unicornstudio.lanball.core.Screen;
import lombok.Data;


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

    public void dispose() {
        stage.dispose();
    }

    public Camera getCamera() {
        return stage.getCamera();
    }

    public void addActor(Actor actor) {
        group.addActor(actor);
    }

    public void unload() {
        if (group != null) {
            group.clear();
        }
    }

}
