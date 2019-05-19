package com.unicornstudio.lanball.model.map.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Settings {

    @JsonProperty("ball")
    private BallSettings ballSettings;

    @JsonProperty("player")
    private PlayerSettings playerSettings;

    private List<Team> teams;

}
