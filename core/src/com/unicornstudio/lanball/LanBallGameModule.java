package com.unicornstudio.lanball;

import com.google.inject.AbstractModule;

public class LanBallGameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LanBallGame.class);
    }

}
