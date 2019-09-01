package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.unicornstudio.lanball.LanBallGame;

import javax.inject.Singleton;

@Singleton
public class Editor extends AbstractLmlView {

    @LmlActor("leftPanel")
    private Window leftPanel;

    @LmlActor("leftPanel")
    private Window editor;

    public Editor() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Editor.lml");
    }

    @Override
    public String getViewId() {
        return "editor";
    }

}
