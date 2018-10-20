package com.unicornstudio.lanball.map;

import com.badlogic.gdx.Gdx;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.BallService;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.WorldService;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.map.settings.Settings;
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

    private MapDto map;

    public void loadMap(String name) {
        map = MapMapper.map(Gdx.files.local(name))
                .orElse(null);
        worldService.create(map);
        ballService.createBall(getMapSettings().getBallSettings());
        entitiesService.createPlayer(map.getSettings().getTeams().get(0));
    }

    public Settings getMapSettings() {
        return map.getSettings();
    }

}
