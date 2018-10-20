package com.unicornstudio.lanball.network.dto;

import com.unicornstudio.lanball.model.TeamType;
import lombok.Data;

@Data
public class PlayerDto {

    private String name;

    private TeamType type;

}
