package com.unicornstudio.lanball.service;

import com.google.inject.Singleton;
import com.unicornstudio.lanball.model.animations.Animation;

import java.util.HashSet;

@Singleton
public class AnimationService {

    private final HashSet<Animation> animations = new HashSet<>();

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public void render(double frameTimeMillis) {
        animations.removeIf(Animation::isFinished);
        animations.iterator().forEachRemaining(animation -> { animation.render(frameTimeMillis); });
    }

}
