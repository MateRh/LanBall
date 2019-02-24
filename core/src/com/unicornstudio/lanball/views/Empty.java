package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.unicornstudio.lanball.LanBallGame;

public class Empty extends AbstractLmlView {


    public Empty() {
        super(LanBallGame.getStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Empty.lml");
    }

    @Override
    public String getViewId() {
        return "empty";
    }
}
