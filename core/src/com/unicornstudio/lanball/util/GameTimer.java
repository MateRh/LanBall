package com.unicornstudio.lanball.util;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GameTimer {

    private long time = 0L;

    private Long lastTick;

    private Long endTime;

    @Setter
    private boolean pause = false;

    private boolean finished = false;

    public GameTimer(long endTime) {
        this.endTime = endTime;
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
        time = time + (System.currentTimeMillis() - lastTick);
        lastTick = System.currentTimeMillis();
    }

    private void idle() {
        lastTick = null;
    }

    private void validate() {
        if (time >= endTime) {
            finished = true;
            time = endTime;
        }
    }

}
