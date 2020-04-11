package com.unicornstudio.lanball.network.model.protocol;


public class NetworkProtocol {

    public static int VERSION;

    static {
        try {
            //VERSION = Integer.valueOf(Gdx.files.internal("version").readString());
            VERSION = 1;
        } catch (Exception e) {
            VERSION = -1;
        }
    }

}
