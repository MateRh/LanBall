package com.unicornstudio.lanball.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class SoundService {

    private HashMap<SoundType, Sound> soundsCache = new HashMap<>();

    public void playSound(SoundType type) {
        if (!soundsCache.containsKey(type)) {
            soundsCache.put(type, Gdx.audio.newSound(Gdx.files.internal(type.getPath())));
        }
        soundsCache.get(type).play();
    }

}
