package com.unicornstudio.lanball.io;

import com.badlogic.gdx.files.FileHandle;

public class MapFileReader {

    public String read(FileHandle file) {
        return file.readString();
    }

}
