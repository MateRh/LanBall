package com.unicornstudio.lanball.model.map.world;

import lombok.Data;

@Data
public class WorldDto {

    private SizeDto size;

    private GroundPlane foreground;

    private GroundPlane background;

    private Gate gates;

}
