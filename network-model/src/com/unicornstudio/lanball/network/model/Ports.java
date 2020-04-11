package com.unicornstudio.lanball.network.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Ports {

    @Getter
    private static List<Integer> list;

    static {
        list = Arrays.asList(
                2033,
                2056,
                2302,
                2303,
                2593,
                2599,
                3659,
                4000,
                5500,
                7133,
                7171,
                7777,
                10480,
                10823,
                15567,
                16567,
                17011,
                19132,
                20560
        );
    }

}
