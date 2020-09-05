package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.server.service.ServerDataService;
import com.unicornstudio.lanball.util.GameTimer;

import java.util.Optional;

@Singleton
public class DebugInformationActor extends Actor {

    @Inject
    private ClientDataService clientDataService;

    @Inject
    private ServerDataService serverDataService;

    private BitmapFont font = new BitmapFont();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getZIndex() != Integer.MAX_VALUE) {
            toFront();
        }
        font.draw(batch, getText(), 10, 160);
    }

    private String getText() {
        return "Client:" +
                "\n  state: " + clientDataService.getGameState() +
                "\n  time: " + clientDataService.getTimerTime() +
                "\n  timer pause: " + Optional.ofNullable(clientDataService.getTimer()).map(GameTimer::isPause).orElse(false) +
                "\nServer: " +
                "\n  state: " + serverDataService.getGameState() +
                "\n  time: " + serverDataService.getTimerTime() +
                "\n  timer pause: " + Optional.ofNullable(serverDataService.getTimer()).map(com.unicornstudio.lanball.commons.GameTimer::isPause);
    }

}
