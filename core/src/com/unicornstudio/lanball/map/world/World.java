package com.unicornstudio.lanball.map.world;

import lombok.Data;

@Data
public class World {

    private Size size;

    private GroundPlane foreground;

    private GroundPlane background;

    private Gate gates;

}
