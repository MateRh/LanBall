package com.unicornstudio.lanball.util.adapter.dto;

import com.unicornstudio.lanball.model.map.elements.CircleElement;
import com.unicornstudio.lanball.model.map.elements.DiskElement;
import com.unicornstudio.lanball.model.map.elements.EdgeElement;
import com.unicornstudio.lanball.model.map.elements.GateArea;
import com.unicornstudio.lanball.model.map.elements.PlayerSpawn;
import com.unicornstudio.lanball.model.map.elements.RectangleElement;
import com.unicornstudio.lanball.model.map.settings.BallSettings;
import com.unicornstudio.lanball.model.map.settings.PlayerSettings;
import com.unicornstudio.lanball.model.map.settings.Team;

public enum EditorElement {

    RECTANGLE("Rectangle", RectangleElement.class),
    EDGE("Edge", EdgeElement.class),
    CIRCLE("Circle", CircleElement.class),
    DISK("Disk", DiskElement.class),
    GATE_AREA("Gate area", GateArea.class),
    PLAYER_SPAWN("Player Spawn", PlayerSpawn.class),
    TEAM("Team", Team.class, 2),
    BALL_SETTINGS("Ball settings", BallSettings.class, 1),
    PLAYER_SETTINGS("Player settings", PlayerSettings.class, 1);

    private String name;

    private Class<?> elementClass;

    private Integer instanceLimit;

    EditorElement(String name, Class<?> elementClass) {
        this.name = name;
        this.elementClass = elementClass;
    }

    EditorElement(String name, Class<?> elementClass, int instanceLimit) {
        this.name = name;
        this.elementClass = elementClass;
        this.instanceLimit = instanceLimit;
    }

    public Class<?> getElementClass() {
        return elementClass;
    }

    public String getName() {
        return name;
    }

    public Integer getInstanceLimit() {
        return instanceLimit;
    }

}
