package com.unicornstudio.lanball.network.server;

import com.unicornstudio.lanball.model.TeamType;
import lombok.Data;

@Data
public class Player {

    private Integer id;

    private String name;

    private Integer ping;

    private TeamType teamType = TeamType.SPECTATORS;

    private Float positionX;

    private Float positionY;

    private Float velocityX;

    private Float velocityY;

    public Player(int id) {
        this.id = id;
    }

    public void updatePositionAndVelocity(Float positionX, Float positionY, Float velocityX, Float velocityY) {
        setPositionX(positionX);
        setPositionY(positionY);
        setVelocityX(velocityX);
        setVelocityY(velocityY);
    }

}
