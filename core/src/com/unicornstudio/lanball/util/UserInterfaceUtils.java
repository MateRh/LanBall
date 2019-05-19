package com.unicornstudio.lanball.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.BusyBar;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.List;

public class UserInterfaceUtils {

    public static VisWindow createWindow(String  title, int width, int height) {
        VisWindow window = new VisWindow(title, true);
        window.setPosition((Gdx.graphics.getWidth() - width)/2, (Gdx.graphics.getHeight() - height)/2);
        window.setSize(width, height);
        window.setModal(true);
        return window;
    }

    public static VisTextButton createTextButton(String text, ClickListener listener) {
        VisTextButton button = new VisTextButton(text);
        button.addListener(listener);
        return button;
    }

    public static VisScrollPane createVisScrollPane(int alignment, Color color, List<Actor> list, EventListener eventListener) {
        VisScrollPane visScrollPane = new VisScrollPane(createVisList(alignment, color, list, eventListener));
        visScrollPane.setStyle(getScrollPaneStyle());
        return visScrollPane;
    }

    public static void addClearSeparator(VisTable table) {
        table.addSeparator().getActor().setColor(Color.CLEAR);
        table.row();
    }

    public static Dialog createLoadingDialog() {
        Dialog dialog = new Dialog("Loading...", VisUI.getSkin());
        BusyBar busyBar = new BusyBar();
        busyBar.setWidth(200);
        busyBar.setHeight(50);
        dialog.add(busyBar);
        return dialog;
    }

    private static VisList createVisList(int alignment, Color color, List<Actor> list, EventListener eventListener) {
        VisList<Actor> visList = new VisList<>();
        visList.setAlignment(alignment);
        visList.setColor(color);
        visList.setItems(list.toArray(new Actor[0]));
        visList.addListener(eventListener);
        return visList;
    }

    private static ScrollPane.ScrollPaneStyle getScrollPaneStyle() {
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = VisUI.getSkin().getDrawable("grey");
        return scrollPaneStyle;
    }

}
