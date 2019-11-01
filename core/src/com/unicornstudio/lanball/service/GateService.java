package com.unicornstudio.lanball.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.unicornstudio.lanball.listner.WorldContactListener;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.client.ClientService;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GateService {

    @Inject
    private WorldService worldService;

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private ClientService clientService;

    @Inject
    private ClientDataService clientDataService;

    @Inject
    private WorldContactListener worldContactListener;

    @Setter
    private Body leftGateSensor;

    @Setter
    private Body rightGateSensor;

    public void createLeftGateSensor(int x, int y, float width, float height) {
        leftGateSensor = createGateBody(x, y, width, height);
    }

    public void createRightGateSensor(int x, int y, int width, int height) {
        rightGateSensor = createGateBody(x, y, width, height);
    }

    public void initialize() {
        if (clientService.isHost()) {
            worldContactListener.addListener(
                    new GateListener(clientService, clientDataService, leftGateSensor, rightGateSensor, entitiesService.getEntity("ball").getPhysicsEntity().getBody()));
        }
    }

    private Body createGateBody(int x, int y, float width, float height) {
        return new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                .world(worldService.getWorld())
                .bodyType(BodyDef.BodyType.StaticBody)
                .position(x, y)
                .sensor(true)
                .shapeType(Shape.Type.Edge)
                .width(width)
                .height(height)
                .build()
                .getBody();
    }


}
