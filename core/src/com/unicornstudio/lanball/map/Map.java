package com.unicornstudio.lanball.map;

import com.unicornstudio.lanball.map.settings.Settings;
import com.unicornstudio.lanball.map.world.World;
import lombok.Data;

@Data
public class Map {

    private Information information;

    private Settings settings;

    private World world;

}
