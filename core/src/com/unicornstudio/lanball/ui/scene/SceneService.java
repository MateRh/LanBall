package com.unicornstudio.lanball.ui.scene;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SceneService {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Scene scene;

    public void showMainMenuScene() {
        remove();
        scene = new MainMenu(this);
        create();
    }

    public void showHostScene() {
        remove();
        scene = new HostScene(this);
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
