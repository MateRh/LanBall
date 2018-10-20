package com.unicornstudio.lanball.stage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.inject.Singleton;

import com.unicornstudio.lanball.Screen;
import lombok.Data;

@Data
@Singleton
public class StageService {

    private Stage stage;

    public void init() {
        stage = new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
        stage.getCamera().position.set(Screen.getHalfWidth(), Screen.getHalfHeight(), 0);
    }


    public void render() {
        stage.act();
        stage.draw();
    }

    public Camera getCamera() {
        return stage.getCamera();
    }

    public void addActor(Actor actor) {
        stage.addActor(actor);
    }

}
