package com.unicornstudio.lanball;

import com.google.inject.AbstractModule;
import com.unicornstudio.lanball.core.Game;

public class LanBallGameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Game.class);
    }

}
