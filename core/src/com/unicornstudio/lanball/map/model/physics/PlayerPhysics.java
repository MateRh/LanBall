package com.unicornstudio.lanball.map.model.physics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayerPhysics {

    @JsonProperty("bCoef")
    private int bCoef;

    private int invMass;

    private int damping;

    private int acceleration;

    private int kickingAcceleration;

    private int kickingDamping;

    private int kickStrength;

}
