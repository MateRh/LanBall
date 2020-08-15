package com.unicornstudio.lanball.server.service;

import com.unicornstudio.lanball.network.model.Ball;

import javax.inject.Singleton;

@Singleton
public class BallService {

    private final Ball ball = new Ball();

    public void updateBall(Float positionX, Float positionY, Float velocityX, Float velocityY) {
        ball.setPositionX(positionX);
        ball.setPositionY(positionY);
        ball.setVelocityX(velocityX);
        ball.setVelocityY(velocityY);
    }

}
