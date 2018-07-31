package com.unicornstudio.lanball.ui.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.unicornstudio.lanball.ui.scene.SceneService;

public class JoinActionListener extends ClickListener {

    private  final SceneService sceneService;

    public JoinActionListener(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        sceneService.showJoinScene();
    }

}
