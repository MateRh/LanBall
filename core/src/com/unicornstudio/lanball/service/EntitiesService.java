package com.unicornstudio.lanball.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.map.MapService;
import com.unicornstudio.lanball.model.map.settings.PlayerSettings;
import com.unicornstudio.lanball.model.map.settings.Team;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.Contestant;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.util.PhysicsEntityCreator;
import com.unicornstudio.lanball.util.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.util.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.util.dto.ShapeDto;
import com.unicornstudio.lanball.model.actors.ContestantActor;
import com.unicornstudio.lanball.model.actors.PlayerActor;

@Singleton
public class EntitiesService {

    public final static short BIT_PLAYER = 0x0001;

    public final static short BIT_BALL = 0x0002;

    public final static short BIT_PLAYER_BOUND = 0x0004;

    public final static short BIT_BALL_BOUND = 0x0008;

    @Inject
    private WorldService worldService;

    @Inject
    private StageService stageService;

    @Inject
    private MapService mapService;

    private Map<String, Entity> entities = new HashMap<>();

    public void addEntity(String key, Entity entity) {
        entities.put(key, entity);
    }

    public Entity getEntity(String key) {
        return entities.get(key);
    }

    public void synchronizeEntitiesPosition() {
        entities.values().forEach(this::synchronizeEntityPosition);
    }

    public PhysicsEntity createEntity(BodyDefinitionDto bodyDefinitionDto, ShapeDto shapeDto, FixtureDefinitionDto fixtureDefinitionDto) {
        return PhysicsEntityCreator.createEntity(worldService.getWorld(), bodyDefinitionDto, shapeDto, fixtureDefinitionDto);
    }

    public void createPlayer(Team team) {
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity playerActorPhysicsEntity = createEntity(
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(Screen.getHalfWidth(), Screen.getHalfHeight()), 1f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 2f, null, null, null),
                new FixtureDefinitionDto(playerSettings.getFriction(), playerSettings.getRestitution(), playerSettings.getDensity(), false, BIT_PLAYER, (short) (BIT_PLAYER | BIT_BALL | BIT_PLAYER_BOUND))
        );
        PhysicsEntity sensor = createEntity(
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(Screen.getHalfWidth(), Screen.getHalfHeight()), 0f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 1.9f, null, null, null),
                new FixtureDefinitionDto(0f, 0f, 0f, true,  BIT_PLAYER, (short) (BIT_PLAYER | BIT_BALL | BIT_PLAYER_BOUND))
        );
        PlayerActor actor = new PlayerActor(team, playerSettings.getRadius());
        stageService.addActor(actor);
        addEntity("player", new Player(actor, playerActorPhysicsEntity, sensor));
    }

    public void createContestant(Integer id, String name, Team team) {
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity contestantActorPhysicsEntity = PhysicsEntityCreator.createEntity(worldService.getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody,
                        new Vector2(Screen.getHalfWidth() + new Random().nextInt(25), Screen.getHalfHeight() + new Random().nextInt(25) ), 1f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 2f, null, null, null),
                new FixtureDefinitionDto(playerSettings.getFriction(), playerSettings.getRestitution(), playerSettings.getDensity(), false,  BIT_PLAYER, (short) (BIT_PLAYER | BIT_BALL | BIT_PLAYER_BOUND))
        );
        ContestantActor actor = new ContestantActor(team, name, playerSettings.getRadius());
        stageService.addActor(actor);
        addEntity("contestant_" + id, new Contestant(actor, contestantActorPhysicsEntity));
    }

    public void updateContestantData(int id, Vector2 position, Vector2 velocity) {
        Contestant contestant = getContestantById(id);

        if (contestant != null) {
            Body body = contestant.getPhysicsEntity().getBody();
            body.setTransform(position, 0f);
            body.setLinearVelocity(velocity);
        }
    }

    public void updatePlayerData(Vector2 position, Vector2 velocity) {
        Player player = (Player) entities.get("player");
        Body body = player.getPhysicsEntity().getBody();
        body.setTransform(position, 0f);
        body.setLinearVelocity(velocity);
    }

    public void updateBall(Vector2 position, Vector2 velocity) {
        Ball ball = (Ball) entities.get("ball");
        Body body = ball.getPhysicsEntity().getBody();
        body.setTransform(position, 0f);
        body.setLinearVelocity(velocity);
    }

    private Contestant getContestantById(Integer id) {
        return (Contestant) entities.getOrDefault("contestant_" + id, null);
    }

    private void synchronizeEntityPosition(Entity entity) {
        Vector2 position = entity.getPhysicsEntity().getBody().getPosition();
        entity.getActor().setPosition(
                (position.x * Screen.getPixelPerMeter() - entity.getActor().getWidth()/2),
                position.y * Screen.getPixelPerMeter() - entity.getActor().getHeight()/2
        );
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.getSensor().getBody().setTransform(position, 0);
        }
    }

}
