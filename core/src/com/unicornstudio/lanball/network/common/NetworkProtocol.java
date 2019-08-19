package com.unicornstudio.lanball.network.common;


import com.badlogic.gdx.Gdx;

public class NetworkProtocol {

    public static int VERSION;

    static {
        try {
            VERSION = Integer.valueOf(Gdx.files.internal("version").readString());
        } catch (Exception e) {
            VERSION = -1;
        }
        System.out.println("Network Protocol Version:" + VERSION);
    }

}
