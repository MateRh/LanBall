package com.unicornstudio.lanball.network.dto;

import com.unicornstudio.lanball.model.TeamType;
import lombok.Data;

@Data
public class PlayerDto {

    private int id;

    private String name;

    private TeamType teamType;

    private boolean remotePlayer;

}
