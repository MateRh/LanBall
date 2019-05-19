package com.unicornstudio.lanball.model.map.settings;

import lombok.Data;

@Data
public class BallSettings {

    private Float positionX;

    private Float positionY;

    private Integer size;

    private Float speed;

    private Float linearDamping;

    private Float friction;

    private Float restitution;

    private Float density;

    private String color;

}
