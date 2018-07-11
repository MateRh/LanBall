package com.unicornstudio.lanball.world.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Element extends Actor {

    private final int x;

    private final int y;

    private final float bCoef;

    private final String cMask;

    private final String cGroup;

}
