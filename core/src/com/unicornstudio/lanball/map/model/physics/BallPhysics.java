package com.unicornstudio.lanball.map.model.physics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BallPhysics {

    private int radius;

    @JsonProperty("bCoef")
    private int bCoef;

    private int invMass;

    private int damping;

    private String color;

    @JsonProperty("cMask")
    private List<String> cMask;

    @JsonProperty("cGroup")
    private List<String> cGroup;

}
