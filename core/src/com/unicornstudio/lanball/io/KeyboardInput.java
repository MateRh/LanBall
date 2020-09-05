package com.unicornstudio.lanball.io;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.model.animations.BallKickAnimation;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.service.AnimationService;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.prefernces.SettingsType;
import com.unicornstudio.lanball.views.Game;
import com.unicornstudio.lanball.views.HostServer;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

public class KeyboardInput {

    private static final float FRAME_TIME = 16666666.6667f;

    private final EntitiesService entitiesService;

    private final ClientService clientService;

    private final ClientDataService clientDataService;

    private final AnimationService animationService;

    private final Preferences preferences;

    private Long lastKickInputMillis = System.currentTimeMillis();

    private Long previousFrameMillis = System.nanoTime();

    @Inject
    public KeyboardInput(EntitiesService entitiesService, ClientService clientService, ClientDataService clientDataService,
            AnimationService animationService) {
        this.entitiesService = entitiesService;
        this.clientService = clientService;
        this.clientDataService = clientDataService;
        this.animationService = animationService;
        preferences = SettingsType.CONTROL.getPreference();
    }

    public void onInput() {
        float scale = (System.nanoTime() - previousFrameMillis) / FRAME_TIME;
        previousFrameMillis = System.nanoTime();
        Body playerBody = getPlayerBody();

        if (playerBody != null) {
            if (isPressed(SettingsKeys.SHOT_CONTROL, SettingsKeys.SHOT_CONTROL_ALTERNATIVE)) {
                onKickInput(playerBody, scale);
            }
            if (isPressed(SettingsKeys.LEFT_CONTROL, SettingsKeys.LEFT_CONTROL_ALTERNATIVE)) {
                onLeftInput(playerBody, scale);
            }
            if (isPressed(SettingsKeys.RIGHT_CONTROL, SettingsKeys.RIGHT_CONTROL_ALTERNATIVE)) {
                onRightInput(playerBody, scale);
            }
            if (isPressed(SettingsKeys.DOWN_CONTROL, SettingsKeys.DOWN_CONTROL_ALTERNATIVE)) {
                onDownInput(playerBody, scale);
            }
            if (isPressed(SettingsKeys.UP_CONTROL, SettingsKeys.UP_CONTROL_ALTERNATIVE)) {
                onUpInput(playerBody, scale);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                switchBetweenGameAndHostServerViews();
            }
        }
    }

    private void onKickInput(Body playerBody, float scale) {
        if (System.currentTimeMillis() - lastKickInputMillis < 500) {
            return;
        }
        Body ballBody = getBallBody();
        Body sensorBody = getPlayerSensor();
        if (ballBody != null && sensorBody != null) {
            getContact(ballBody, sensorBody).ifPresent(
                    contact -> {
                        clientService.sendRequest(
                                ClientRequestBuilder.createPlayerKickBallClientRequest(
                                        clientDataService.getRemotePlayer().getId(),
                                        getForceFromAngle(getAngleBetweenTwoBodies(ballBody, playerBody)).scl(4.5f).scl(scale),
                                        contact.getWorldManifold().getNormal())
                        );
                    }
            );
            Actor playerActor = getPlayerActor();
            if (playerActor != null) {
                clientService.sendRequest(ClientRequestBuilder.createPlayerKeyPressClientRequest(
                        clientDataService.getRemotePlayer().getId()));
                lastKickInputMillis = System.currentTimeMillis();
                animationService.addAnimation(new BallKickAnimation(playerActor));
            }
        }
    }

    private void onLeftInput(Body playerBody, float scale) {
        Vector2 position = playerBody.getPosition();
        applyImpulse(playerBody,-Player.VELOCITY * scale, 0, position.x, position.y);
    }

    private void onRightInput(Body playerBody, float scale) {
        Vector2 position = playerBody.getPosition();
        applyImpulse(playerBody,Player.VELOCITY * scale, 0, position.x, position.y);
    }

    private void onUpInput(Body playerBody, float scale) {
        Vector2 position = playerBody.getPosition();
        applyImpulse(playerBody,0, Player.VELOCITY * scale, position.x, position.y);
    }

    private void onDownInput(Body playerBody, float scale) {
        Vector2 position = playerBody.getPosition();
        applyImpulse(playerBody,0, -Player.VELOCITY * scale, position.x, position.y);
    }


    private void applyImpulse(Body body, float impulseX, float impulseY, float pointX, float pointY) {
        body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, true);
        body.setLinearVelocity(body.getLinearVelocity().limit(Player.MAX_VELOCITY));
    }

    private void switchBetweenGameAndHostServerViews() {
        if (clientDataService.getGameState() == GameState.LOBBY) {
            return;
        }
        if (((LanBallGame) Gdx.app.getApplicationListener()).getCurrentView() instanceof Game) {
            ((LanBallGame) Gdx.app.getApplicationListener()).setView(HostServer.class);
        } else if (((LanBallGame) Gdx.app.getApplicationListener()).getCurrentView() instanceof HostServer) {
            ((LanBallGame) Gdx.app.getApplicationListener()).setView(Game.class);
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

    private Actor getPlayerActor() {
        Entity entity = entitiesService.getEntity("player");
        if (entity == null) {
            return null;
        }
        return entity.getActor();
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

    private Double calculateAngle(double x1, double y1, double x2, double y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle = angle + Math.ceil( -angle / 360 ) * 360;
        return angle;
    }

    private Vector2 getForceFromAngle(float angle) {
        double a = Math.toRadians(90 - angle);
        double dx = Math.cos(a);
        double dy = Math.sin(a);
        return new Vector2((float) dx, (float) dy);
    }

    private boolean isPressed(String key, String alternativeKey) {
        return Gdx.input.isKeyPressed(preferences.getInteger(key)) || Gdx.input.isKeyPressed(preferences.getInteger(alternativeKey));
    }

}
