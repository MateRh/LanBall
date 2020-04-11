package com.unicornstudio.lanball.model.animations;

public interface Animation {

    void render(double frameTimeMillis);

    boolean isFinished();

}
