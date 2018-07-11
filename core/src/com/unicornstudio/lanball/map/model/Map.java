package com.unicornstudio.lanball.map.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicornstudio.lanball.map.model.physics.BallPhysics;
import com.unicornstudio.lanball.map.model.physics.PlayerPhysics;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Map {

    private String name;

    private int width;

    private int height;

    private int spawnDistance;

    @JsonProperty("bg")
    private Background background;

    private BallPhysics ballPhysics;

    private PlayerPhysics playerPhysics;

    private List<Vertex> vertexes;

    private List<Segment> segments;

    private List<Goal> goals;

    private List<Disc> discs;

    private List<Plane> planes;



}
