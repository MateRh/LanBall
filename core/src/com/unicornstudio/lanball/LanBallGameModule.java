package com.unicornstudio.lanball;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.unicornstudio.lanball.ui.scene.SceneService;

public class LanBallGameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SceneService.class).asEagerSingleton();
    }

}
