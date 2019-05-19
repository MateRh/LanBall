package com.unicornstudio.lanball.model.map.settings;

import lombok.Data;

@Data
public class PlayerSettings {

    private Float radius;

    private Float velocity;

    private Float maxVelocity;

    private Float linearDamping;

    private Float friction;

    private Float restitution;

    private Float density;

}
