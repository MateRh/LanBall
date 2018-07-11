package com.unicornstudio.lanball.io;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import lombok.Getter;

public class MapChooserListener extends FileChooserAdapter {

    @Getter
    private FileHandle file;


    @Override
    public void canceled () {
        file = null;
    }

    @Override
    public void selected (Array<FileHandle> files) {
        file = files.first();
    }

}
