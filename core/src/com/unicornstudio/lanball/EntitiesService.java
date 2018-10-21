package com.unicornstudio.lanball;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.map.MapService;
import com.unicornstudio.lanball.map.settings.PlayerSettings;
import com.unicornstudio.lanball.map.settings.Team;
import com.unicornstudio.lanball.model.Contestant;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.utils.PhysicsEntityCreator;
import com.unicornstudio.lanball.utils.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.utils.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.utils.dto.ShapeDto;
import com.unicornstudio.lanball.video.ContestantActor;
import com.unicornstudio.lanball.video.PlayerActor;

@Singleton
public class EntitiesService {

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
        entities.values().stream().forEach(this::synchronizeEntityPosition);
    }

    public PhysicsEntity createEntity(BodyDefinitionDto bodyDefinitionDto, ShapeDto shapeDto, FixtureDefinitionDto fixtureDefinitionDto) {
        return PhysicsEntityCreator.createEntity(worldService.getWorld(), bodyDefinitionDto, shapeDto, fixtureDefinitionDto);
    }

    public void createPlayer(Team team) {
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity playerActorPhysicsEntity = createEntity(
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(Screen.getHalfWidth(), Screen.getHalfHeight()), 1f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 2f, null, null, null),
                new FixtureDefinitionDto(playerSettings.getFriction(), playerSettings.getRestitution(), playerSettings.getDensity(), false)
        );
        PhysicsEntity sensor = createEntity(
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody, new Vector2(Screen.getHalfWidth(), Screen.getHalfHeight()), 0f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 1.9f, null, null, null),
                new FixtureDefinitionDto(0f, 0f, 0f, true)
        );
        PlayerActor actor = new PlayerActor(team, playerSettings.getRadius());
        stageService.addActor(actor);
        addEntity("player", new Player(actor, playerActorPhysicsEntity, sensor));
    }

    public void createContestant(Integer id, String name, Team team) {
        PlayerSettings playerSettings = mapService.getMapSettings().getPlayerSettings();
        PhysicsEntity contestantActorPhysicsEntity = PhysicsEntityCreator.createEntity(worldService.getWorld(),
                new BodyDefinitionDto(BodyDef.BodyType.DynamicBody,
                        new Vector2(Screen.getHalfWidth(), Screen.getHalfHeight()), 1f),
                new ShapeDto(Shape.Type.Circle, playerSettings.getRadius() / 2f, null, null, null),
                new FixtureDefinitionDto(playerSettings.getFriction(), playerSettings.getRestitution(), playerSettings.getDensity(), false)
        );
        ContestantActor actor = new ContestantActor(team, name, playerSettings.getRadius());
        stageService.addActor(actor);
        addEntity("contestant_" + id, new Contestant(actor, contestantActorPhysicsEntity));
    }

    private void synchronizeEntityPosition(Entity entity) {
        Vector2 position = entity.getPhysicsEntity().getBody().getPosition();
        entity.getActor().setPosition(
                position.x * Screen.getPixelPerMeter() - entity.getActor().getWidth()/2,
                position.y * Screen.getPixelPerMeter() - entity.getActor().getHeight()/2
        );
        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.getSensor().getBody().setTransform(position, 0);
        }
    }

}
