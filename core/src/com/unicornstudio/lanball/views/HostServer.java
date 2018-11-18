package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.layout.DragPane;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.unicornstudio.lanball.LanBallGame;

public class HostServer extends AbstractLmlView {



    @LmlActor("teamOneDragPane")
    private DragPane teamOneDragPane;

    @LmlActor("teamSpectatorsDragPane")
    private DragPane teamSpectatorsDragPane;

    @LmlActor("teamTwoDragPane")
    private DragPane teamTwoDragPane;

    @LmlActor("timeLimitTextField")
    private VisTextField timeLimitTextField;

    @LmlActor("scoreLimitTextField")
    private VisTextField scoreLimitTextField;

    @LmlActor("pickMapButton")
    private Button pickMapButton;

    @LmlActor("startTheGameButton")
    private Button startTheGameButton;

    public HostServer() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/HostServer.lml");
    }

    @Override
    public String getViewId() {
        return "hostServer";
    }

    @LmlAfter
    private void initialize() {

    }
}
