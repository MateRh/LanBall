package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.unicornstudio.lanball.LanBallGame;

public class Game extends AbstractLmlView {

    @LmlActor("core")
    private Window window;

    public Game() {
        super(LanBallGame.getStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Game.lml");
    }

    @Override
    public String getViewId() {
        return "game";
    }

    public Window getWindow() {
        return window;
    }
}
