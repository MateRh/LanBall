package com.unicornstudio.lanball.model.map.elements;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CircleElement.class, name = "circle"),
        @JsonSubTypes.Type(value = DiskElement.class, name = "disk"),
        @JsonSubTypes.Type(value = EdgeElement.class, name = "edge"),
        @JsonSubTypes.Type(value = RectangleElement.class, name = "rectangle")
})
public interface MapElement {

    MapElementType getType();

}
