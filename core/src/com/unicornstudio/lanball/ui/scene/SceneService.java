package com.unicornstudio.lanball.ui.scene;

import com.google.inject.Singleton;

@Singleton
public class SceneService {

    private Scene scene;

    public void showMainMenuScene() {
        remove();
        scene = new MainMenu();
        create();
    }

    public void showHostScene() {
        remove();
        scene = new HostScene();
        create();
    }

    public void showJoinScene() {
        remove();
        scene = new JoinScene();
        create();
    }

    private void remove() {
        if (scene != null) {
            scene.delete();
        }
    }

    private void create() {
        scene.create(stage);
    }

}
