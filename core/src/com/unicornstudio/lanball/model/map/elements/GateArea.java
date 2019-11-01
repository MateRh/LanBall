package com.unicornstudio.lanball.model.map.elements;

import com.unicornstudio.lanball.model.TeamType;
import lombok.Data;

@Data
public class GateArea implements MapElement {

    private TeamType team;

    private int x;

    private int y;

    private int width;

    private int height;

    @Override
    public MapElementType getType() {
        return null;
    }
}
