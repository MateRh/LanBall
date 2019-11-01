package com.unicornstudio.lanball.model.map.elements;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DiskElement extends Element implements MapElement {

    private int borderSize = 0;

    private FigureType figureType;

    private String color;

    @Override
    public MapElementType getType() {
        return MapElementType.DISK;
    }
}
