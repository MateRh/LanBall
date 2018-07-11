package com.unicornstudio.lanball.map.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vertex {

    private int x;

    private int y;

    @JsonProperty("bCoef")
    private int bCoef;

    private int curve;

    private String trait;

    private String color;

    @JsonProperty("cMask")
    private List<String> cMask;

}
