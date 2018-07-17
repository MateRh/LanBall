package com.unicornstudio.lanball.utils.dto;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BodyDefinitionDto {

    private BodyDef.BodyType type;

    private Vector2 position;

    private Float linearDamping;

}
