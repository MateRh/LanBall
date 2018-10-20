package com.unicornstudio.lanball.ui.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unicornstudio.lanball.ui.scene.SceneService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuActionListener extends ClickListener {

    @Inject
    private SceneService sceneService;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        sceneService.showMainMenuScene();
    }

}
