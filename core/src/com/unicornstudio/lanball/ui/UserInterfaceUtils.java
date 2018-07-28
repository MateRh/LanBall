package com.unicornstudio.lanball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class UserInterfaceUtils {

    public static VisWindow createWindow(String  title, int width, int height) {
        VisWindow window = new VisWindow(title, true);
        window.setPosition((Gdx.graphics.getWidth() - width)/2, (Gdx.graphics.getHeight() - height)/2);
        window.setSize(width, height);
        window.setModal(true);
        return window;
    }

    public static VisScrollPane createVisScrollPane(int alignment, Color color, String[] list) {
        VisScrollPane visScrollPane = new VisScrollPane(createVisList(alignment, color, list));
        visScrollPane.setStyle(getScrollPaneStyle());
        return visScrollPane;
    }

    public static void addClearSeparator(VisTable table) {
        table.addSeparator().getActor().setColor(Color.CLEAR);
        table.row();
    }

    private static VisList createVisList(int alignment, Color color, String[] list) {
        VisList<String> visList = new VisList<>();
        visList.setAlignment(alignment);
        visList.setColor(color);
        visList.setItems(mockArray());
        return visList;
    }

    private static ScrollPane.ScrollPaneStyle getScrollPaneStyle() {
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = VisUI.getSkin().getDrawable("grey");
        return scrollPaneStyle;
    }

    private static String[] mockArray() {
        String[] array = new String[5];
        array[0] = "Test 1";
        array[1] = "Test 2";
        array[2] = "Test 3";
        array[3] = "Test 4";
        array[4] = "Test 5";
        return array;
    }

}
