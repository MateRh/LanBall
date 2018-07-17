package com.unicornstudio.lanball.video;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.settings.Team;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.utils.TextureCreator;

public class PlayerActor extends Actor {

    private final Texture texture;

    public PlayerActor(Team team) {
        setWidth(Player.PLAYER_RADIUS);
        setHeight(Player.PLAYER_RADIUS);
        texture = provideTexture(team.getColor());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }

    private Texture provideTexture(String color) {
        return TextureCreator.createCircleTexture(Player.PLAYER_RADIUS.intValue(), color);
    }
}
