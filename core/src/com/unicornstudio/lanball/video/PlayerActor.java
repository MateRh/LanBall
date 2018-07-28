package com.unicornstudio.lanball.video;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.settings.Team;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.utils.TextureCreator;
import lombok.Setter;

@Setter
public class PlayerActor extends Actor {

    private final Texture texture;

    private PhysicsEntity physicsEntity;

    public PlayerActor(Team team) {
        setWidth(Player.PLAYER_RADIUS);
        setHeight(Player.PLAYER_RADIUS);
        texture = provideTexture(team.getColor());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
        if (physicsEntity != null) {
            Vector2 velocity = physicsEntity.getBody().getLinearVelocity();
            new BitmapFont().draw(batch, "x: " + velocity.x + ", y: " + velocity.y, 100, 660);
            new BitmapFont().draw(batch, "x: " + velocity.x + ", y: " + velocity.y, 100, 660);
        }
    }

    private Texture provideTexture(String color) {
        return TextureCreator.createCircleTexture(Player.PLAYER_RADIUS.intValue(), color);
    }
}
