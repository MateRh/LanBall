package com.unicornstudio.lanball.map;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.BallService;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.WorldService;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.map.settings.Settings;
import com.unicornstudio.lanball.stage.StageService;
import com.unicornstudio.lanball.video.FPSCounterActor;
import lombok.Data;

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
        ballService.createBall(getMapSettings().getBallSettings());
        entitiesService.createPlayer(map.getSettings().getTeams().get(0));
        stageService.addActor(new FPSCounterActor());
    }

    public void loadMap(String name) {
        loadMap(Gdx.files.local(name));
    }

    public Settings getMapSettings() {
        return map.getSettings();
    }

}
