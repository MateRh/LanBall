package com.unicornstudio.lanball.map.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {

    private int x;

    private int v0;

    private int v1;

    private int intv1;

    private float curve;

    private String color;

    private String trait;

    @JsonProperty(value = "vis")
    private boolean visible;

}
