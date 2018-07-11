package com.unicornstudio.lanball.map.model.traits;

import lombok.Data;

import java.util.List;

@Data
public class Stanchion {

    private int radius;

    private int invMass;

    private int bCoef;

    private List<String> cMask;

}
