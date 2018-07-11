package com.unicornstudio.lanball.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.unicornstudio.lanball.map.MapScene;
import com.unicornstudio.lanball.map.model.Map;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.video.renders.Renderers;
import lombok.Getter;

public class MapChooser {

    private final static String DEFAULT_PREFS_NAME = "com.unicornstudio.lanball.filechooser";
    private final static String FILE_TYPE_FILTER_DESCRIPTION = "HaxBall map (*.hbs)";
    private final static String FILE_TYPE_FILTER_EXTENSION = "hbs";
    private FileChooser fileChooser;
    private VisTextField textField;
    @Getter
    private FileHandle file;

    public MapChooser() {
        textField = new VisTextField();
        fileChooser = new FileChooser(FileChooser.Mode.OPEN);
        configure();
    }

    public void show(Stage stage) {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(textField);
        stage.addActor(fileChooser);
    }

    private void configure() {
        FileChooser.setDefaultPrefsName(DEFAULT_PREFS_NAME);
        fileChooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        fileChooser.setFileTypeFilter(getTypeFilter());

        fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected (Array<FileHandle> files) {
                file = files.first();
                textField.setText(file.file().getAbsolutePath());
                Map map = MapMapper.map(file).orElse(null);
                System.out.println(file.file().getAbsolutePath());
                System.out.println(map.getName());
                System.out.println(map.getBackground().getType());
                Renderers.add(new MapScene(map));
            }

            @Override
            public void canceled () {
            }

        });
    }

    private FileTypeFilter getTypeFilter() {
        FileTypeFilter fileTypeFilter = new FileTypeFilter(false);
        fileTypeFilter.addRule(FILE_TYPE_FILTER_DESCRIPTION, FILE_TYPE_FILTER_EXTENSION);
        return fileTypeFilter;
    }

}
