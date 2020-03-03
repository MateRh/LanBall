package com.unicornstudio.lanball.service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.inject.Singleton;

import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.actors.FPSCounterActor;
import lombok.Data;


@Data
@Singleton
public class StageService {

    private Group group;

    public Stage getStage() {
        return createStage();
    }

    public void addActor(Actor actor) {
        group.addActor(actor);
    }

    public void unload() {
        if (group != null) {
            group.clear();
        }
    }

    public void addFPSCounterActor() {
        group.getStage().addActor(new FPSCounterActor());
    }

    private Stage createStage() {
        Stage stage = new Stage(new ScreenViewport(new OrthographicCamera(Screen.getWidth(), Screen.getHeight())));
        stage.getCamera().position.set(Screen.getHalfWidth(), Screen.getHalfHeight(), 0);
        return stage;
    }

}
