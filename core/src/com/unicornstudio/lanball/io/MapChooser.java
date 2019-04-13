package com.unicornstudio.lanball.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.google.inject.Inject;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.unicornstudio.lanball.map.MapService;
import com.unicornstudio.lanball.network.client.ClientRequestBuilder;
import com.unicornstudio.lanball.network.client.ClientService;
import lombok.Getter;

public class MapChooser {

    @Inject
    private MapService mapService;

    @Inject
    private ClientService clientService;

    private final static String DEFAULT_PREFS_NAME = "com.unicornstudio.lanball.filechooser";
    private final static String FILE_TYPE_FILTER_DESCRIPTION = "LanBall map (*.lan)";
    private final static String FILE_TYPE_FILTER_EXTENSION = "lan";
    private FileChooser fileChooser;

    @Getter
    private FileHandle file;

    public void initialize() {
        fileChooser = new FileChooser(FileChooser.Mode.OPEN);
        configure();
    }

    public void show(Stage stage) {
        Gdx.input.setInputProcessor(stage);
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
                mapService.loadMap(file);
                clientService.sendRequest(ClientRequestBuilder.createMapLoadClientRequest(file));
            }

            @Override
            public void canceled () {
                fileChooser.remove();
            }

        });
    }

    private FileTypeFilter getTypeFilter() {
        FileTypeFilter fileTypeFilter = new FileTypeFilter(false);
        fileTypeFilter.addRule(FILE_TYPE_FILTER_DESCRIPTION, FILE_TYPE_FILTER_EXTENSION);
        return fileTypeFilter;
    }

}
