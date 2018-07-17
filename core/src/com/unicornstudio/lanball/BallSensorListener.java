package com.unicornstudio.lanball;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import javax.inject.Inject;

public class BallSensorListener implements ContactListener {

    private final EntitiesService entitiesService;

    @Inject
    public BallSensorListener(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
        System.out.println("sdasdasda");
    }


    @Override
    public void beginContact(Contact contact) {
        System.out.println("beginContact");
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
