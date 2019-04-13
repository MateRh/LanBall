package com.unicornstudio.lanball.map;


import com.badlogic.gdx.files.FileHandle;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.BallService;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.WorldService;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.map.settings.Settings;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.video.FPSCounterActor;
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

    public void initialize(Map<TeamType, List<PlayerDto>> players) {
        worldService.initialize();
        ballService.createBall(getMapSettings().getBallSettings());
        players.forEach((key, value) -> value.forEach(this::createPlayer));
        stageService.addActor(new FPSCounterActor());
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
