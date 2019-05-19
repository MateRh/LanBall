package com.unicornstudio.lanball.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import lombok.Setter;

public class GateListener implements ContactListener {

    private final ClientService clientService;

    private final Body leftGateSensor;

    private final Body rightGateSensor;

    private final Body ballBody;

    @Setter
    private boolean contactEnabled = true;

    public GateListener(ClientService clientService, Body leftGateSensor, Body rightGateSensor, Body ballBody) {
        this.clientService = clientService;
        this.leftGateSensor = leftGateSensor;
        this.rightGateSensor = rightGateSensor;
        this.ballBody = ballBody;
    }

    @Override
    public void beginContact(Contact contact) {
        if (!contactEnabled) {
            return;
        }
        if ((contact.getFixtureA().getBody().equals(leftGateSensor) && contact.getFixtureB().getBody().equals(ballBody)) ||
                        (contact.getFixtureA().getBody().equals(ballBody) && contact.getFixtureB().getBody().equals(leftGateSensor))) {
            contactEnabled = false;
            clientService.sendRequest(
                    ClientRequestBuilder.createGateContactClientRequest(TeamType.TEAM2));
        }
        if ((contact.getFixtureA().getBody().equals(rightGateSensor) && contact.getFixtureB().getBody().equals(ballBody)) ||
                        (contact.getFixtureA().getBody().equals(ballBody) && contact.getFixtureB().getBody().equals(rightGateSensor))) {
            contactEnabled = false;
            clientService.sendRequest(
                    ClientRequestBuilder.createGateContactClientRequest(TeamType.TEAM1));
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
