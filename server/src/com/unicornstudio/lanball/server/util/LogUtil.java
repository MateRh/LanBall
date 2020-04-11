package com.unicornstudio.lanball.server.util;

import java.time.LocalTime;

public class LogUtil {

    public static void printConsole(String text) {
        System.out.println(String.format("[%s] %s", getTimestamp(), text));
    }

    private static String getTimestamp() {
        return LocalTime.now().toString();
    }

}
