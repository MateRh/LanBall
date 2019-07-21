package com.unicornstudio.lanball.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import lombok.Setter;

@Singleton
public class BallListener implements ContactListener {

    @Inject
    private ClientService clientService;

    @Setter
    private Body ballBody;

    @Setter
    private Body playerBody;

    private boolean enabled = false;

    public void setStatus(boolean status) {
        enabled = status;
    }

    @Override
    public void beginContact(Contact contact) {
        if (!clientService.isConnected()) {
            return;
        }
        if (enabled
                && ( (contact.getFixtureA().getBody().equals(ballBody) && contact.getFixtureB().getBody().equals(playerBody))
                    || (contact.getFixtureB().getBody().equals(ballBody) && contact.getFixtureA().getBody().equals(playerBody)))) {
            enabled = false;
            clientService.sendRequest(
                    ClientRequestBuilder.createBallContactRequest()
            );
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
