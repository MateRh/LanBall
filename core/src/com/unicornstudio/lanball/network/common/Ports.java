package com.unicornstudio.lanball.network.common;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.Getter;

public class Ports {

    @Getter
    private static List<Integer> list;

    static {
        list = Lists.newArrayList(
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
