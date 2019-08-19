package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.server.ServerDataService;

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
        font.draw(batch, getText(), 10, 80);
    }

    private String getText() {
        return "Client:" +
                "\n  state: " + clientDataService.getGameState() +
                "\nServer: " +
                "\n  state: " + serverDataService.getGameState();
    }

}
