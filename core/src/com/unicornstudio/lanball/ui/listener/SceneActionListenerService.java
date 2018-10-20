package com.unicornstudio.lanball.ui.listener;

import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Getter
@Singleton
public class SceneActionListenerService {

    @Inject
    private MainMenuActionListener mainMenuActionListener;

    @Inject
    private JoinSceneActionListener joinSceneActionListener;

    @Inject
    private HostSceneActionListener hostSceneActionListener;

}
