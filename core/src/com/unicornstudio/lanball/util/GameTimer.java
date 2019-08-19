package com.unicornstudio.lanball.util;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GameTimer {

    private Long lastTick;

    @Setter
    private Long time;

    @Setter
    private boolean pause = false;

    private boolean finished = false;

    public GameTimer(long time) {
        this.time = time;
    }

    public void tick() {
        if (finished) {
            return;
        }
        if (pause) {
            idle();
        } else {
            update();
            validate();
        }
    }

    private void update() {
        if (lastTick == null) {
            lastTick = System.currentTimeMillis();
            return;
        }
        time = time - (System.currentTimeMillis() - lastTick);
        lastTick = System.currentTimeMillis();
    }

    private void idle() {
        lastTick = null;
    }

    private void validate() {
        if (time <= 0) {
            finished = true;
        }
    }

}
