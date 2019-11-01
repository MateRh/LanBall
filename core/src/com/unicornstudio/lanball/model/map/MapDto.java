package com.unicornstudio.lanball.model.map;

import com.unicornstudio.lanball.model.map.elements.MapElement;
import com.unicornstudio.lanball.model.map.settings.Settings;
import com.unicornstudio.lanball.model.map.world.WorldDto;
import lombok.Data;

import java.util.List;

@Data
public class MapDto {

    private Information information;

    private Settings settings;

    private WorldDto world;

    private List<MapElement> elements;

}
