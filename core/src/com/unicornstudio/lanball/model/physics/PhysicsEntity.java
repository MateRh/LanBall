package com.unicornstudio.lanball.model.physics;


import com.badlogic.gdx.physics.box2d.Body;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhysicsEntity {

    private Body body;

}
