package com.unicornstudio.lanball.io.mappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicornstudio.lanball.model.map.MapDto;

import java.io.IOException;
import java.util.Optional;

public class MapMapper {

    public static Optional<MapDto> map(FileHandle mapFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        try {
        return Optional.ofNullable(mapper.readValue(mapFile.file(), MapDto.class));
        } catch (IOException e) {
            Gdx.app.error("MapMapper", "Error during parsing map file.", e);
            return Optional.empty();
        }
    }

    public static Optional<MapDto> map(String mapString) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        try {
        return Optional.ofNullable(mapper.readValue(mapString, MapDto.class));
        } catch (IOException e) {
            Gdx.app.error("MapMapper", "Error during parsing map data.", e);
            return Optional.empty();
        }
    }

}
