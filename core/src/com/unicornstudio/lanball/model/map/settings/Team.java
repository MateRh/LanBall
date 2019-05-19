package com.unicornstudio.lanball.model.map.settings;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private String name;

    private String color;

    private List<Position> positions;

}
