package com.unicornstudio.lanball.util.dto;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import lombok.Data;

@Data
public class PhysicsEntityDto {

    private BodyDef.BodyType bodyType;

    private Vector2 position;

    private Float linearDamping;

    private Shape.Type shapeType;

    private Float radius;

    private Float width;

    private Float height;

    private Float friction;

    private Float restitution;

    private Float density;

    private boolean sensor;

    private Short categoryBits;

    private Short maskBits;

}
