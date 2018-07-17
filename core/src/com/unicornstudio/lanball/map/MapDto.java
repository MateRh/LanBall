package com.unicornstudio.lanball.map;

import com.unicornstudio.lanball.map.settings.Settings;
import com.unicornstudio.lanball.map.world.WorldDto;
import lombok.Data;

@Data
public class MapDto {

    private Information information;

    private Settings settings;

    private WorldDto world;

}
