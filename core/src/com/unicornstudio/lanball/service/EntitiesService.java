package com.unicornstudio.lanball.service;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.model.map.MapService;
import com.unicornstudio.lanball.model.map.settings.PlayerSettings;
import com.unicornstudio.lanball.model.map.settings.Team;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.Contestant;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.model.actors.ContestantActor;
import com.unicornstudio.lanball.model.actors.PlayerActor;

@Singleton
public class EntitiesService {

    public final static short BIT_PLAYER_TEAM1 = 0x0001;

    public final static short BIT_PLAYER_TEAM2 = 0x0002;

    public final static short BIT_BALL = 0x0004;

    public final static short BIT_PLAYER_BOUND = 0x0008;

    public final static short BIT_BALL_BOUND = 0x0016;

    @Inject
    private WorldService worldService;

    @Inject
    private StageService stageService;

    @Inject
    private MapService mapService;

    @Inject
    private BallListener ballListener;

    private Map<String, Entity> entities = new HashMap<>();

    public void addEntity(String key, Entity entity) {
        entities.put(key, entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public Entity getEntity(String key) {
        return entities.get(key);
    }

    public void synchronizeEntitiesPosition() {
        entities.values().forEach(this::synchronizeEntityPosition);
    }

    public void createPlayer(Team team, TeamType teamType) {
        short playerBit = getPlayerBit(teamType);
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity playerActorPhysicsEntity = createPlayerPhysicsEntity(playerSettings, playerBit);
        PhysicsEntity sensor = new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                .world(worldService.getWorld())
                .bodyType(BodyDef.BodyType.DynamicBody)
                .position(0, 0)
                .sensor(true)
                .categoryBits(playerBit)
                .maskBits((short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2 | BIT_BALL | BIT_PLAYER_BOUND))
                .shapeType(Shape.Type.Circle)
                .radius(playerSettings.getRadius() / 1.9f)
                .build();
        PlayerActor actor = new PlayerActor(team, playerSettings.getRadius());
        stageService.addActor(actor);
        addEntity("player", new Player(actor, playerActorPhysicsEntity, sensor));
        ballListener.setPlayerBody(playerActorPhysicsEntity.getBody());
    }

    public void createContestant(Integer id, String name, Team team, TeamType teamType) {
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity contestantActorPhysicsEntity = createPlayerPhysicsEntity(playerSettings, getPlayerBit(teamType));
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

    public void removeContestant(int id) {
        Contestant contestant = getContestantById(id);
        if (contestant != null) {
            contestant.getActor().remove();
            removeEntity(contestant);
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
                (position.x * Screen.getPixelPerMeter() - entity.getActor().getWidth()/2f),
                position.y * Screen.getPixelPerMeter() - entity.getActor().getHeight()/2f
        );
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.getSensor().getBody().setTransform(position, 0);
        }
    }

    private short getPlayerBit(TeamType teamType) {
        if (teamType.equals(TeamType.TEAM1)) {
            return BIT_PLAYER_TEAM1;
        } else {
            return BIT_PLAYER_TEAM2;
        }
    }

    private PhysicsEntity createPlayerPhysicsEntity(PlayerSettings playerSettings, short categoryBits) {
        return new com.unicornstudio.lanball.builder.PhysicsEntityBuilder()
                .world(worldService.getWorld())
                .bodyType(BodyDef.BodyType.DynamicBody)
                .position(0, 0)
                .linearDamping(playerSettings.getLinearDamping())
                .friction(playerSettings.getFriction())
                .restitution(playerSettings.getRestitution())
                .density(playerSettings.getDensity())
                .categoryBits(categoryBits)
                .maskBits((short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2 | BIT_BALL | BIT_PLAYER_BOUND))
                .shapeType(Shape.Type.Circle)
                .radius(playerSettings.getRadius() / 2f)
                .build();
    }

}
