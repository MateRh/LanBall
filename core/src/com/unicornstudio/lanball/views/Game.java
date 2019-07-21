package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.service.StageService;
import com.unicornstudio.lanball.util.FontProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class Game extends AbstractLmlView {

    @Inject
    private ClientDataService clientDataService;

    @Inject
    private StageService stageService;

    @LmlActor("core")
    private Window window;

    @LmlActor("hudWindow")
    private VisTable hudTable;

    @LmlActor("timeLabel")
    private VisLabel timeLabel;

    @LmlActor("teamScore")
    private VisLabel teamScore;

    @LmlActor("team1Square")
    private VisImage team1Square;

    @LmlActor("team2Square")
    private VisImage team2Square;

    private DateFormat timeFormat = new SimpleDateFormat("mm:ss");

    public Game() {
        super(LanBallGame.getStage());
    }

    @Override
    public void render(final float delta) {
        updateHudElements();
        super.render(delta);
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

    @LmlAfter
    private void initialize() {
        stageService.setGroup(window);
        Label.LabelStyle labelStyle = new Label.LabelStyle(timeLabel.getStyle());
        labelStyle.font = FontProvider.provide("DS-DIGIB", 32, Color.WHITE);
        timeLabel.setStyle(labelStyle);
        teamScore.setStyle(labelStyle);
    }

    private void updateHudElements() {
        timeLabel.setText(timeFormat.format(new Date(clientDataService.getTimeLimit())));
        teamScore.setText(clientDataService.getTeam1Score() + " - " + clientDataService.getTeam2Score());
    }

}
