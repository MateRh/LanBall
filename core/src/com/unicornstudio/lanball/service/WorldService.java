package com.unicornstudio.lanball.service;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.model.map.MapDto;
import com.unicornstudio.lanball.model.map.world.SizeDto;
import com.unicornstudio.lanball.model.map.world.WorldDto;
import com.unicornstudio.lanball.model.MapWorld;
import com.unicornstudio.lanball.model.physics.PhysicsEntity;
import com.unicornstudio.lanball.util.PhysicsEntityBuilder;
import com.unicornstudio.lanball.util.PhysicsEntityDtoBuilder;
import com.unicornstudio.lanball.util.dto.BodyDefinitionDto;
import com.unicornstudio.lanball.util.dto.FixtureDefinitionDto;
import com.unicornstudio.lanball.util.dto.PhysicsEntityDto;
import com.unicornstudio.lanball.util.dto.ShapeDto;
import com.unicornstudio.lanball.model.actors.MapBackground;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.unicornstudio.lanball.model.actors.MapBackground.GATE_SIZE;
import static com.unicornstudio.lanball.model.actors.MapBackground.LINE_SPACE;
import static com.unicornstudio.lanball.service.EntitiesService.BIT_PLAYER_TEAM1;
import static com.unicornstudio.lanball.service.EntitiesService.BIT_PLAYER_TEAM2;

@Data
@Singleton
public class WorldService {

    private static final Vector2 WORLD_GRAVITY_VECTOR = new Vector2(0, 0);

    private static final int GATE_HEIGHT = 100;

    private final World world;

    @Inject
    private GateService gateService;

    @Inject
    private StageService stageService;

    private MapWorld mapWorld;

    private List<PhysicsEntity> initialRoundBounds = new ArrayList<>();

    public WorldService() {
        world = createWorld();
    }


    public void create(MapDto mapDto) {
        mapWorld = new MapWorld();
        mapWorld.setWorld(world);
        mapWorld.setMapBackground(createMapBackground(mapDto.getWorld()));
        mapWorld.setPhysicsEntities(_createWorldBounds(mapDto.getWorld().getSize()));
    }

    public void initialize() {
        stageService.addActor(getMapBackground());
    }

    public World getWorld() {
        return mapWorld.getWorld();
    }

    public boolean isCreated() {
        return Objects.nonNull(mapWorld);
    }

    public MapBackground getMapBackground() {
        return mapWorld.getMapBackground();
    }

    public void setInitialRoundBoundsActive(boolean active) {
        initialRoundBounds.forEach(physicsEntity -> physicsEntity.getBody().setActive(active));
    }

    public void updateInitialRoundBoundsFilter(TeamType teamType) {
        initialRoundBounds.forEach(
                physicsEntity -> physicsEntity.getBody().getFixtureList().
                        forEach(fixture -> fixture.getFilterData().maskBits = getPlayerBit(teamType))
        );
    }

    private World createWorld() {
        return new World(WORLD_GRAVITY_VECTOR, false);
    }

    private MapBackground createMapBackground(WorldDto worldDto) {
        return new MapBackground(worldDto.getSize(), worldDto.getForeground());
    }

    private List<PhysicsEntity> _createWorldBounds(SizeDto sizeDto) {

        /*
        
            Z ciekawych rzeczy:

                Box2D to silnik 2D z tzw perspektywy "z boku",
                 ta gra używa perspektywy 2D z góry,
                 z tego powodu kordynaty świata wyglądają tak:

                 ( 0, wysokość ) * --------------------------------- * ( szerokość, wysokość )
                                 |                                   |
                                 |                                   |
                                 |                                   |
                       ( 0, 0 )  * --------------------------------- * ( szrokośc, 0 )

         */

        List<PhysicsEntityDto> bounds = new ArrayList<>();
        createWorldInnerBounds(bounds, sizeDto.getWidth(), sizeDto.getHeight());
        createWorldOuterBounds(bounds, sizeDto.getWidth(), sizeDto.getHeight());
        createCenterBounds(sizeDto.getWidth(), sizeDto.getHeight());

        return bounds.stream()
                .map(dto -> PhysicsEntityBuilder.buildPhysicsEntity(getWorld(), dto))
                .collect(Collectors.toList());
    }

    private void createWorldOuterBounds(List<PhysicsEntityDto> bounds, int width, int height) {
        FixtureDefinitionDto fixtureDefinition = PhysicsEntityDtoBuilder.buildFixtureDefinitionDto(
                0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, EntitiesService.BIT_BALL);

        FixtureDefinitionDto gateFixtureDefinition = PhysicsEntityDtoBuilder.buildFixtureDefinitionDto(
                0f, 1f, 1f, false, EntitiesService.BIT_BALL_BOUND, (short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2 | EntitiesService.BIT_BALL));

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(width - 2 * LINE_SPACE, 1f),
                        buildBodyDefinitionDto(LINE_SPACE, LINE_SPACE),
                        fixtureDefinition
                )
        );

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(width - 2 * LINE_SPACE, 1f),
                        buildBodyDefinitionDto(LINE_SPACE, height - LINE_SPACE),
                        fixtureDefinition
                )
        );

        int halfHeight = (height - GATE_SIZE - LINE_SPACE * 2) / 2;


        // left top

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, halfHeight),
                        buildBodyDefinitionDto(LINE_SPACE, height - halfHeight - LINE_SPACE),
                        fixtureDefinition
                )
        );

        // left bottom

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, halfHeight),
                        buildBodyDefinitionDto(LINE_SPACE, LINE_SPACE),
                        fixtureDefinition
                )
        );

        // right top

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, halfHeight),
                        buildBodyDefinitionDto(width - LINE_SPACE, height - halfHeight - LINE_SPACE),
                        fixtureDefinition
                )
        );


        // right bottom

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, halfHeight),
                        buildBodyDefinitionDto(width - LINE_SPACE, LINE_SPACE),
                        fixtureDefinition
                )
        );


        // left gate top

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(LINE_SPACE / 2, 1f),
                        buildBodyDefinitionDto( LINE_SPACE / 2, height - halfHeight - LINE_SPACE),
                        gateFixtureDefinition
                )
        );


        // left gate bottom

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(LINE_SPACE / 2, 1f),
                        buildBodyDefinitionDto(LINE_SPACE / 2, LINE_SPACE + halfHeight),
                        gateFixtureDefinition
                )
        );

        // left gate center

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, GATE_HEIGHT + LINE_SPACE),
                        buildBodyDefinitionDto(LINE_SPACE / 2, LINE_SPACE + halfHeight),
                        gateFixtureDefinition
                )
        );

        // right gate top

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(LINE_SPACE / 2, 1f),
                        buildBodyDefinitionDto( width - LINE_SPACE, height - halfHeight - LINE_SPACE),
                        gateFixtureDefinition
                )
        );


        // right gate bottom

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(LINE_SPACE / 2, 1f),
                        buildBodyDefinitionDto(width - LINE_SPACE, LINE_SPACE + halfHeight),
                        gateFixtureDefinition
                )
        );

        // right gate center

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, GATE_HEIGHT + LINE_SPACE),
                        buildBodyDefinitionDto(width - LINE_SPACE / 2, LINE_SPACE + halfHeight),
                        gateFixtureDefinition
                )
        );

        gateService.createLeftGateSensor(LINE_SPACE, LINE_SPACE + halfHeight, 1f, GATE_HEIGHT + LINE_SPACE);
        gateService.createRightGateSensor(width - LINE_SPACE , LINE_SPACE + halfHeight, 1, GATE_HEIGHT + LINE_SPACE);
    }

    private void createWorldInnerBounds(List<PhysicsEntityDto> bounds, int width, int height) {
        FixtureDefinitionDto fixtureDefinition = PhysicsEntityDtoBuilder.buildFixtureDefinitionDto(
                0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND, (short) (BIT_PLAYER_TEAM1 | BIT_PLAYER_TEAM2));

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(width, 1f),
                        buildBodyDefinitionDto(0, 0),
                        fixtureDefinition
                )
        );

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, height),
                        buildBodyDefinitionDto(0, 0),
                        fixtureDefinition
                )
        );

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(width, 1f),
                        buildBodyDefinitionDto(0, height),
                        fixtureDefinition
                )
        );

        bounds.add(
                PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                        buildShapeDto(1f, height),
                        buildBodyDefinitionDto(width, 0),
                        fixtureDefinition
                )
        );
    }

    private void createCenterBounds(int width, int height) {
        FixtureDefinitionDto fixtureDefinition = PhysicsEntityDtoBuilder.buildFixtureDefinitionDto(
                0f, 1f, 1f, false, EntitiesService.BIT_PLAYER_BOUND, BIT_PLAYER_TEAM1);
        int x = LINE_SPACE + (width - 2 * LINE_SPACE) / 2;
        int y = LINE_SPACE + (height - 2 * LINE_SPACE) / 2;

        initialRoundBounds.add(PhysicsEntityBuilder.buildPhysicsEntity(getWorld(),
            PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                    PhysicsEntityDtoBuilder.buildShapeDto(Shape.Type.Circle, null, null, GATE_SIZE * 0.75f),
                    buildBodyDefinitionDto(x, y),
                    fixtureDefinition
        )));

        initialRoundBounds.add(PhysicsEntityBuilder.buildPhysicsEntity(getWorld(),
            PhysicsEntityDtoBuilder.buildPhysicsEntityDto(
                    buildShapeDto(1f, height),
                    buildBodyDefinitionDto(width / 2, 0),
                    fixtureDefinition
        )));

        initialRoundBounds.forEach(physicsEntity -> physicsEntity.getBody().setActive(false));
        updateInitialRoundBoundsFilter(TeamType.TEAM2);
    }

    private BodyDefinitionDto buildBodyDefinitionDto(int x, int y) {
        return PhysicsEntityDtoBuilder.buildBodyDefinitionDto(BodyDef.BodyType.StaticBody, new Vector2(x, y), 1f);
    }

    private ShapeDto buildShapeDto(float width, float height) {
        return PhysicsEntityDtoBuilder.buildShapeDto(Shape.Type.Edge, width, height, null);
    }


    private short getPlayerBit(TeamType teamType) {
        if (teamType.equals(TeamType.TEAM1)) {
            return BIT_PLAYER_TEAM1;
        } else {
            return BIT_PLAYER_TEAM2;
        }
    }

}
