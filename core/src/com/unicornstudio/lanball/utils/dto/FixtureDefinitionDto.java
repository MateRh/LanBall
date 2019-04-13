package com.unicornstudio.lanball.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FixtureDefinitionDto {

    private Float friction;

    private Float restitution;

    private Float density;

    private boolean sensor;

    private Short categoryBits;

    private Short maskBits;

}
