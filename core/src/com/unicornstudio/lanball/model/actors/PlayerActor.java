package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.map.settings.Team;
import com.unicornstudio.lanball.util.TextureCreator;


public class PlayerActor extends Actor {

    private final Texture texture;

    public PlayerActor(Team team, Float radius) {
        setWidth(radius);
        setHeight(radius);
        texture = provideTexture(team.getColor(), radius);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }

    private Texture provideTexture(String color, Float radius) {
        return TextureCreator.createCircleTexture(radius.intValue(), color, 4);
    }
}
