package com.unicornstudio.lanball.map.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plane {

    private int dist;

    @JsonProperty("bCoef")
    private int bCoef;

    private String trait;

    private List<Integer> normal;

}
