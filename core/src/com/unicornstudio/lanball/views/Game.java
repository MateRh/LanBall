package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.util.FontProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class Game extends AbstractLmlView {

    @Inject
    private ClientDataService clientDataService;

    @LmlActor("core")
    private Window window;

    @LmlActor("hudWindow")
    private VisTable hudTable;

    private VisLabel timeLabel;

    private VisLabel teamScore;

    private Timer updateTimer = new Timer("GameViewMainTimer");

    private DateFormat timeFormat = new SimpleDateFormat("mm:ss");

    public Game() {
        super(LanBallGame.getStage());
    }

    @Override
    public void dispose() {
        updateTimer.purge();
        super.dispose();
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
        timeLabel = new VisLabel();
        Label.LabelStyle timeLabelStyle = timeLabel.getStyle();
        timeLabelStyle.font = FontProvider.provide("DS-DIGIB", 32, Color.WHITE);
        timeLabel.setStyle(timeLabelStyle);

        Label.LabelStyle scoreLabelStyle = timeLabel.getStyle();
        scoreLabelStyle.font = FontProvider.provide("DS-DIGIB", 32, Color.WHITE);
        teamScore = new VisLabel();
        teamScore.setStyle(scoreLabelStyle);

        VisTextButton redTeamButton = new VisTextButton("");
        redTeamButton.setColor(Color.RED);
        VisTextButton blueRedButton = new VisTextButton("");
        blueRedButton.setColor(Color.BLUE);

        hudTable.add(redTeamButton);
        hudTable.add(teamScore);
        hudTable.add(blueRedButton);
        for (int i = 0; i < 10; i++) {
            hudTable.add(new Separator());
        }
        hudTable.add(timeLabel);
        hudTable.setWidth(768);
        updateTimer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        updateHudElements();
                    }
                }, 500, 500);
    }

    private void updateHudElements() {
        timeLabel.setText(timeFormat.format(new Date(clientDataService.getTimeLimit())));
        teamScore.setText(clientDataService.getTeam1Score() + " - " + clientDataService.getTeam2Score());
    }

}
