package com.unicornstudio.lanball.ui.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;
import com.unicornstudio.lanball.ui.scene.SceneService;



public class HostActionListener extends ClickListener {

    @Inject
    private SceneService sceneService;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        sceneService.showHostScene();
    }

}
