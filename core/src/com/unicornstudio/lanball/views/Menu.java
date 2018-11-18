package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.unicornstudio.lanball.LanBallGame;

public class Menu extends AbstractLmlView {

    public Menu() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Menu.lml");
    }

    @Override
    public String getViewId() {
        return "menu";
    }
}
