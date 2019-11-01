package com.unicornstudio.lanball.model.map.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EdgeElement extends Element implements MapElement {

    private FigureType figureType;

    private String color;

    @Override
    public MapElementType getType() {
        return MapElementType.EDGE;
    }
}
