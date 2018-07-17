package com.unicornstudio.lanball.utils.dto;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShapeDto {

    private Shape.Type type;

    private Float radius;

    private Vector2[] vertices;

    private Vector2 v1;

    private Vector2 v2;

}
