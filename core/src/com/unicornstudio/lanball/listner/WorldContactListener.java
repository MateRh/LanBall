package com.unicornstudio.lanball.listner;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class WorldContactListener implements ContactListener {

    private final Set<ContactListener> listeners;

    public WorldContactListener() {
        listeners = new HashSet<>();
    }

    public void addListener(ContactListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContactListener listener) {
        listeners.remove(listener);
    }

    public void clear() {
        listeners.clear();
    }

    @Override
    public void beginContact(Contact contact) {
        listeners.forEach(listener -> listener.beginContact(contact));
    }

    @Override
    public void endContact(Contact contact) {
        listeners.forEach(listener -> listener.endContact(contact));
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        listeners.forEach(listener -> listener.preSolve(contact, oldManifold));
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        listeners.forEach(listener -> listener.postSolve(contact, impulse));
    }
}
