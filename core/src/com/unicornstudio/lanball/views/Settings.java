package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.unicornstudio.lanball.LanBallGame;

public class Settings extends AbstractLmlView {

    public Settings() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Settings.lml");
    }

    @Override
    public String getViewId() {
        return "settings";
    }
}
