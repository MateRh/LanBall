package com.unicornstudio.lanball.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import lombok.Setter;

public class GateListener implements ContactListener {

    private final ClientService clientService;

    private final ClientDataService clientDataService;

    private final Body leftGateSensor;

    private final Body rightGateSensor;

    private final Body ballBody;

    @Setter
    private boolean contactEnabled = true;

    public GateListener(ClientService clientService, ClientDataService clientDataService, Body leftGateSensor, Body rightGateSensor, Body ballBody) {
        this.clientService = clientService;
        this.clientDataService = clientDataService;
        this.leftGateSensor = leftGateSensor;
        this.rightGateSensor = rightGateSensor;
        this.ballBody = ballBody;
    }

    @Override
    public void beginContact(Contact contact) {
        if (!clientDataService.getGameState().equals(GameState.IN_PROGRESS)) {
            return;
        }
        if ((contact.getFixtureA().getBody().equals(leftGateSensor) && contact.getFixtureB().getBody().equals(ballBody)) ||
                        (contact.getFixtureA().getBody().equals(ballBody) && contact.getFixtureB().getBody().equals(leftGateSensor))) {
            clientService.sendRequest(
                    ClientRequestBuilder.createGateContactClientRequest(TeamType.TEAM2));
        }
        if ((contact.getFixtureA().getBody().equals(rightGateSensor) && contact.getFixtureB().getBody().equals(ballBody)) ||
                        (contact.getFixtureA().getBody().equals(ballBody) && contact.getFixtureB().getBody().equals(rightGateSensor))) {
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
