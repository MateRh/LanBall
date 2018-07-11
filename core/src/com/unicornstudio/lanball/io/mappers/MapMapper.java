package com.unicornstudio.lanball.io.mappers;

import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicornstudio.lanball.map.model.Map;

import java.io.IOException;
import java.util.Optional;

public class MapMapper {

    public static Optional<Map> map(FileHandle mapFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        try {
        return Optional.ofNullable(mapper.readValue(mapFile.file(), Map.class));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
