package com.unicornstudio.lanball.model.map.elements;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Element {

    private String id;

    private float x;

    private float y;

    private int width;

    private int height;

    private float linearDamping = 0f;

    private boolean fixedRotation = true;

    private float friction = 0f;

    private float restitution = 0f;

    private float density = 0f;

    private int radius;

    private boolean sensor;

    private FigureType figureType;

    private FunctionalType functionalType;

    private CollisionBits categoryBits;

    private List<CollisionBits> maskBits = new ArrayList<>();

}
