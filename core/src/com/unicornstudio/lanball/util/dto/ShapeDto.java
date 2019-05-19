package com.unicornstudio.lanball.util.dto;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShapeDto {

    private Shape.Type type;

    private Float radius;

    private Vector2[] vertices;

    private Vector2 v1;

    private Vector2 v2;

    private Float width;

    private Float height;

    public ShapeDto(Shape.Type type, Float radius, Vector2[] vertices, Vector2 v1, Vector2 v2) {
        this.type = type;
        this.radius = radius;
        this.vertices = vertices;
        this.v1 = v1;
        this.v2 = v2;
    }
}
