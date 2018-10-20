package com.unicornstudio.lanball.ui.scene;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.stage.StageService;

@Singleton
public class SceneService {

    @Inject
    private StageService stageService;

    @Inject
    private MainMenu mainMenuScene;

    @Inject
    private HostScene hostScene;

    @Inject
    private JoinScene joinScene;

    private Scene scene;

    public void showMainMenuScene() {
        remove();
        scene = mainMenuScene;
        create();
    }

    public void showHostScene() {
        remove();
        scene = hostScene;
        create();
    }

    public void showJoinScene() {
        remove();
        scene = joinScene;
        create();
    }

    private void remove() {
        if (scene != null) {
            scene.delete();
        }
    }

    private void create() {
        scene.create(stageService.getStage());
    }

}
