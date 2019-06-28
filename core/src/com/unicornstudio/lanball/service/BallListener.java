package com.unicornstudio.lanball.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.google.inject.Inject;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;

public class BallListener implements ContactListener {

    @Inject
    private ClientService clientService;

    private final Body ballBody;

    private boolean enabled = false;

    public BallListener(Body ballBody) {
        this.ballBody = ballBody;
    }

    public void setStatus(boolean status) {
        enabled = status;
    }

    @Override
    public void beginContact(Contact contact) {
        if (enabled && (contact.getFixtureA().getBody().equals(ballBody) || contact.getFixtureB().getBody().equals(ballBody))) {
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
