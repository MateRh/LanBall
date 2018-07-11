package com.unicornstudio.lanball.video.renders;

import java.util.ArrayList;
import java.util.List;

public class Renderers {

    private static List<Renderer> renderers = new ArrayList<>();

    public static void add(Renderer renderer) {
        renderers.add(renderer);
    }

    public static List<Renderer> getRenderers() {
        return renderers;
    }
}
