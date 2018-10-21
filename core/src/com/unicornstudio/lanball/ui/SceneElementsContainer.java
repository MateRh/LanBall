package com.unicornstudio.lanball.ui;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.HashMap;
import java.util.Map;

public class SceneElementsContainer {

    private final Map<String, Group> elements = new HashMap<String, Group>();

    public Group get(String key) {
        return elements.getOrDefault(key, null);
    }

    public Group add(String key, Group element) {
        if (!elements.containsKey(key)) {
            elements.put(key, element);
        }
        return element;
    }

}
