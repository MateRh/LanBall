package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Singleton;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.prefernces.SettingsAdapter;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.prefernces.SettingsType;
import com.unicornstudio.lanball.prefernces.VideoSettings;

@Singleton
public class Settings extends AbstractLmlView {

    @LmlActor("settingsNameTextField")
    private VisTextField nameTextField;

    @LmlActor("settingsApplyButton")
    private VisTextButton applyButton;

    @LmlActor("resolutionsSelectBox")
    private SelectBox<String> selectBox;

    @LmlActor("fullScreenCheckBox")
    private VisCheckBox fullScreenCheckBox;

    public Settings() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Settings.lml");
    }

    @Override
    public String getViewId() {
        return "settings";
    }

    @LmlAfter
    private void load() {
        nameTextField.setText(SettingsAdapter.getString(SettingsType.GLOABL, SettingsKeys.NICKNAME));
        selectBox.setItems(new VideoSettings().getDisplayModes());
        selectBox.setSelected(SettingsAdapter.getString(SettingsType.VIDEO, SettingsKeys.DISPLAY_MODE));
        fullScreenCheckBox.setChecked(SettingsAdapter.getBoolean(SettingsType.VIDEO, SettingsKeys.FULL_SCREEN));
        applyButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                save();
                new VideoSettings().apply(selectBox.getSelected(), fullScreenCheckBox.isChecked());
            }

        });
    }

    private void save() {
        SettingsAdapter.putString(SettingsType.GLOABL, SettingsKeys.NICKNAME, nameTextField.getText());
        SettingsAdapter.putString(SettingsType.VIDEO, SettingsKeys.DISPLAY_MODE, selectBox.getSelected());
        SettingsAdapter.putBoolean(SettingsType.VIDEO, SettingsKeys.FULL_SCREEN, fullScreenCheckBox.isChecked());
        SettingsType.GLOABL.getPreference().flush();
        SettingsType.VIDEO.getPreference().flush();
    }

}