package com.unicornstudio.lanball.service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.unicornstudio.lanball.network.client.ClientDataService;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.util.PhysicsEntityBuilder;
import com.unicornstudio.lanball.util.PhysicsEntityDtoBuilder;

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

    private Body leftGateSensor;

    private Body rightGateSensor;

    public void createLeftGateSensor(int x, int y, float width, float height) {
        leftGateSensor = createGateBody(x, y, width, height);
    }

    public void createRightGateSensor(int x, int y, int width, int height) {
        rightGateSensor = createGateBody(x, y, width, height);
    }

    public void initialize() {
        if (clientService.isHost()) {
            worldService.getMapWorld().getWorld().setContactListener(
                    new GateListener(clientService, clientDataService, leftGateSensor, rightGateSensor, entitiesService.getEntity("ball").getPhysicsEntity().getBody()));
        }
    }

    private Body createGateBody(int x, int y, float width, float height) {
        return PhysicsEntityBuilder.buildPhysicsEntity(worldService.getWorld(),
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        PhysicsEntityDtoBuilder.buildShapeDto(Shape.Type.Edge, width, height, null),
                        PhysicsEntityDtoBuilder.buildBodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(x, y), 1f),
                        PhysicsEntityDtoBuilder.buildFixtureDefinitionDto(
                                0f, 0f, 0f, true, null, null))).getBody();
    }


}
