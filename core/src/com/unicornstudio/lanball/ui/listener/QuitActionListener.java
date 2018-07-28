package com.unicornstudio.lanball.ui.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class QuitActionListener extends ClickListener {


    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
    }

}
