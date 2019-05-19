package com.unicornstudio.lanball.model.map;


import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.io.Input;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.service.GateService;
import com.unicornstudio.lanball.service.BallService;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.service.WorldService;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.model.map.settings.Settings;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.service.StageService;
import com.unicornstudio.lanball.model.actors.FPSCounterActor;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Map;

@Data
@Singleton
public class MapService {

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

    private MapDto map;

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

    public void loadMap(Input file) {
        map = MapMapper.map(file.getInputStream())
                .orElse(null);
        worldService.create(map);
    }

    public void initialize(Map<TeamType, List<PlayerDto>> players) {
        worldService.initialize();
        ballService.createBall(getMapSettings().getBallSettings());
        players.forEach((key, value) -> value.forEach(this::createPlayer));
        stageService.addActor(new FPSCounterActor());
        gateService.initialize();
    }

    private void createPlayer(PlayerDto player) {
        if (player.isRemotePlayer()) {
            entitiesService.createPlayer(map.getSettings().getTeams().get(player.getTeamType().getType()));
        } else {
            entitiesService.createContestant(player.getId(), player.getName(),
                    map.getSettings().getTeams().get(player.getTeamType().getType()));
        }
    }

    public Settings getMapSettings() {
        return map.getSettings();
    }

}
