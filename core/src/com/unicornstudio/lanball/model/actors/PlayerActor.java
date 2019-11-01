package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.map.settings.Team;

public class PlayerActor extends Actor {

    private final Texture texture;

    public PlayerActor(Team team, Float radius) {
        setWidth(radius);
        setHeight(radius);
        setColor(Color.valueOf(team.getColor()));
        texture = new Texture(new Pixmap(Gdx.files.internal("images/player.png")), true);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.setColor(Color.WHITE);
        super.draw(batch, parentAlpha);
    }

}
