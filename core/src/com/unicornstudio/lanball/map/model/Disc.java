package com.unicornstudio.lanball.map.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Disc {

    private int radius;

    private String color;

    @JsonProperty("bCoef")
    private int bCoef;

    private String trait;

    private List<Integer> pos;

}
