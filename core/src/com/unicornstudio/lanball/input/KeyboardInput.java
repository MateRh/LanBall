package com.unicornstudio.lanball.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.Player;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class KeyboardInput {

    private EntitiesService entitiesService;

    @Inject
    public KeyboardInput(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
    }

    public void onInput() {
        Body playerBody = getPlayerBody();
        if (playerBody != null) {
            Vector2 velocity = playerBody.getLinearVelocity();
            Vector2 position = playerBody.getPosition();

            if (Gdx.input.isKeyPressed(Input.Keys.A) && velocity.x > -Player.MAX_VELOCITY) {
                playerBody.applyLinearImpulse(-Player.VELOCITY, 0, position.x, position.y, true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D) && velocity.x < Player.MAX_VELOCITY) {
                playerBody.applyLinearImpulse(Player.VELOCITY, 0, position.x, position.y, true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S) && velocity.y > -Player.MAX_VELOCITY) {
                playerBody.applyLinearImpulse(0, -Player.VELOCITY, position.x, position.y, true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W) && velocity.y < Player.MAX_VELOCITY) {
                playerBody.applyLinearImpulse(0, Player.VELOCITY, position.x, position.y, true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                Body ballBody = getBallBody();
                Body sensorBody = getPlayerSensor();
                getContact(ballBody, sensorBody).ifPresent(
                        contact -> {
                            Vector2 force = getForceFromAngle(getAngleBetweenTwoBodies(ballBody, sensorBody));
                         //   ballBody.applyLinearImpulse(force.x, force.y, ballPosition.x, ballPosition.y, false);
                            ballBody.applyForceToCenter(force, true);
                        }
                );

            }
        }
    }


    private float getAngleBetweenTwoBodies(Body ballBody, Body playerBody) {
        return calculateAngle(playerBody.getPosition().x, playerBody.getPosition().y, ballBody.getPosition().x, ballBody.getPosition().y).floatValue();
    }

    private Optional<Contact> getContact(Body ballBody, Body playerBody) {
        return Arrays.stream(playerBody.getWorld().getContactList().items)
            .filter(contact -> filterContact(contact, ballBody.getFixtureList().first(), playerBody.getFixtureList().first()))
            .findFirst();
    }

    private boolean filterContact(Contact contact, Fixture ballFixture, Fixture playerFixture) {
        return ((contact != null) && (contact.getFixtureA() == playerFixture && contact.getFixtureB() == ballFixture ||
                (contact.getFixtureB() == playerFixture && contact.getFixtureA() == ballFixture)));
    }

    private Body getPlayerBody() {
        Entity entity = entitiesService.getEntity("player");
        if (entity == null) {
            return null;
        }
        return entity.getPhysicsEntity().getBody();
    }

    private Body getPlayerSensor() {
        Player player = (Player) entitiesService.getEntity("player");
        if (player == null) {
            return null;
        }
        return player.getSensor().getBody();
    }

    private Body getBallBody() {
        Entity entity = entitiesService.getEntity("ball");
        if (entity == null) {
            return null;
        }
        return entity.getPhysicsEntity().getBody();
    }

    private Double calculateAngle(double x1, double y1, double x2, double y2)
    {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle = angle + Math.ceil( -angle / 360 ) * 360;

        return angle;
    }

    private Vector2 getForceFromAngle(float angle) {
        double a = Math.toRadians(90 - angle);

        Double dx = Math.cos(a);
        Double dy = Math.sin(a);

        return new Vector2(dx.floatValue()*1000, dy.floatValue()*1000);
    }

}
