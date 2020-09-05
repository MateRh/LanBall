package com.unicornstudio.lanball.model.map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.io.Input;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.builder.EntityFactory;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.map.elements.FunctionalType;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.model.PlayerDto;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.service.GateService;
import com.unicornstudio.lanball.service.BallService;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.service.WorldService;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.model.map.settings.Settings;
import com.unicornstudio.lanball.service.StageService;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

@Data
@Singleton
public class MapService {

    //private static final String DEFAULT_MAP_FILE_NAME = "default.lan";
    private static final String DEFAULT_MAP_FILE_NAME = "default_v2.lan";

    @Inject
    private WorldService worldService;

    @Inject
    private BallService ballService;

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private StageService stageService;

    @Inject
    private GateService gateService;

    @Inject
    private ClientService clientService;

    private MapDto map;

    public void loadDefaultMap() {
        clientService.sendRequestTCP(ClientRequestBuilder.createMapLoadClientRequest(Gdx.files.internal(DEFAULT_MAP_FILE_NAME)));
    }

    public void loadMap(FileHandle fileHandle) {
        map = MapMapper.map(fileHandle)
                .orElse(null);
        worldService.create(map);
    }

    public void loadMap(String mapData) {
        map = MapMapper.map(mapData)
                .orElse(null);
        worldService.create(map);
    }

    public void loadMap(File file) {
        map = MapMapper.map(new FileHandle(file))
                .orElse(null);
        worldService.create(map);
    }

    public void initialize(Map<TeamType, List<PlayerDto>> players) {
        worldService.initialize();
        EntityFactory factory = new EntityFactory(worldService.getWorld());
        map.getElements().forEach(
                mapElement -> {
                    Entity entity = factory.create(mapElement);
                    stageService.addActor(entity.getActor());
                    if (entity.getFunctionalType() != null) {
                        switch (entity.getFunctionalType()) {
                            case MIDDLE_ELEMENT:
                                worldService.addToInitialRoundBounds(entity.getPhysicsEntity());
                                break;
                            case TEAM1_GATE_AREA:
                                gateService.setLeftGateSensor(entity.getPhysicsEntity().getBody());
                                break;
                            case TEAM2_GATE_AREA:
                                gateService.setRightGateSensor(entity.getPhysicsEntity().getBody());
                                break;
                            default:
                                break;
                        }
                    }
                }
        );
        ballService.createBall(getMapSettings().getBallSettings());
        players.forEach((key, value) -> value.forEach(this::createPlayer));
        gateService.initialize();
    }

    public void dispose() {
        worldService.dispose();
    }

    private void createPlayer(PlayerDto player) {
        if (player.isRemotePlayer()) {
            entitiesService.createPlayer(map.getSettings().getTeams().get(player.getTeamType().getType()), player.getTeamType());
        } else {
            entitiesService.createContestant(player.getId(), player.getName(),
                    map.getSettings().getTeams().get(player.getTeamType().getType()), player.getTeamType());
        }
    }

    public Settings getMapSettings() {
        return map.getSettings();
    }

}
