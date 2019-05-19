package com.unicornstudio.lanball.core;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.views.Menu;

public class GlobalActions implements ActionContainer {

    @LmlAction("toMenu")
    void setViewToMenu() {
        ((LanBallGame) Gdx.app.getApplicationListener()).setView(Menu.class);
    }
}
