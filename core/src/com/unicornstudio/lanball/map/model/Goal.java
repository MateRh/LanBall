package com.unicornstudio.lanball.map.model;

import lombok.Data;

import java.util.List;

@Data
public class Goal {

    private String team;

    private List<Integer> p0;

    private List<Integer> p1;

}
