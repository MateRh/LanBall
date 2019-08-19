package com.unicornstudio.lanball.io;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.dto.Host;
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

    private final EntitiesService entitiesService;

    @Inject
    private ClientService clientService;

    @Inject
    private ClientDataService clientDataService;

    private Preferences preferences;

    private Long lastKickInputMillis = System.currentTimeMillis();

    @Inject
    public KeyboardInput(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
        preferences = SettingsType.CONTROL.getPreference();
    }

    public void onInput() {
        Body playerBody = getPlayerBody();
        if (playerBody != null) {
            Vector2 velocity = playerBody.getLinearVelocity();
            Vector2 position = playerBody.getPosition();

            if (isPressed(SettingsKeys.SHOT_CONTROL, SettingsKeys.SHOT_CONTROL_ALTERNATIVE)) {
                if (System.currentTimeMillis() - lastKickInputMillis < 500) {
                    return;
                }
                Body ballBody = getBallBody();
                Body sensorBody = getPlayerSensor();
                if (ballBody != null && sensorBody != null) {
                    getContact(ballBody, sensorBody).ifPresent(
                            contact -> {
                                lastKickInputMillis = System.currentTimeMillis();
                                clientService.sendRequest(
                                        ClientRequestBuilder.createPlayerKickBallClientRequest(
                                                clientDataService.getRemotePlayer().getId(),
                                                getForceFromAngle(getAngleBetweenTwoBodies(ballBody, playerBody)).scl(4.5f),
                                                contact.getWorldManifold().getNormal())
                                );
                            }
                    );
                }
            }

            if (velocity.len() < Player.MAX_VELOCITY) {
                if (isPressed(SettingsKeys.LEFT_CONTROL, SettingsKeys.LEFT_CONTROL_ALTERNATIVE)) {
                    playerBody.applyLinearImpulse(-Player.VELOCITY, 0, position.x, position.y, true);
                }

                if (isPressed(SettingsKeys.RIGHT_CONTROL, SettingsKeys.RIGHT_CONTROL_ALTERNATIVE)) {
                    playerBody.applyLinearImpulse(Player.VELOCITY, 0, position.x, position.y, true);
                }

                if (isPressed(SettingsKeys.DOWN_CONTROL, SettingsKeys.DOWN_CONTROL_ALTERNATIVE)) {
                    playerBody.applyLinearImpulse(0, -Player.VELOCITY, position.x, position.y, true);
                }

                if (isPressed(SettingsKeys.UP_CONTROL, SettingsKeys.UP_CONTROL_ALTERNATIVE)) {
                    playerBody.applyLinearImpulse(0, Player.VELOCITY, position.x, position.y, true);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                if (clientDataService.getGameState() == GameState.LOBBY) {
                    return;
                }
                if (((LanBallGame) Gdx.app.getApplicationListener()).getCurrentView() instanceof Game) {
                    ((LanBallGame) Gdx.app.getApplicationListener()).setView(HostServer.class);
                } else if (((LanBallGame) Gdx.app.getApplicationListener()).getCurrentView() instanceof HostServer) {
                    ((LanBallGame) Gdx.app.getApplicationListener()).setView(Game.class);
                }
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
