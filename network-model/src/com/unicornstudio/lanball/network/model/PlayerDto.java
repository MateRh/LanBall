package com.unicornstudio.lanball.network.model;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import lombok.Data;

@Data
public class PlayerDto {

    private int id;

    private String name;

    private TeamType teamType;

    private boolean remotePlayer;

}
