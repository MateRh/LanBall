package com.unicornstudio.lanball.model.map.elements;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import lombok.Data;

@Data
public class PlayerSpawn implements MapElement {

    private TeamType type;

    private int x;

    private int y;

    @Override
    public MapElementType getType() {
        return null;
    }

}
