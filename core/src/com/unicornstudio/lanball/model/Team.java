package com.unicornstudio.lanball.model;

import com.badlogic.gdx.graphics.Color;
import lombok.Data;

@Data
public class Team {

    private String name;

    private TeamType teamType;

    private Color color;

}
