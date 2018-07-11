package com.unicornstudio.lanball.map.model.traits;

import lombok.Data;

import java.util.List;

@Data
public class BallArea {

    private boolean vis;

    private int bCoef;

    private List<String> cMask;

}
