package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.unicornstudio.lanball.model.map.settings.Team;
import com.unicornstudio.lanball.util.FontProvider;
import com.unicornstudio.lanball.util.TextureCreator;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class ContestantActor extends Actor {

    private final static int NICKNAME_Y_OFFSET = 20;

    private final Texture texture;

    private final BitmapFont nickNameFont;

    private final String nickName;

    public ContestantActor(Team team, String name, Float radius) {
        setWidth(radius);
        setHeight(radius);
        texture = provideTexture(team.getColor(), radius);
        nickNameFont = provideFont();
        nickName = StringUtils.truncate(name, 8);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
        nickNameFont.draw(batch, nickName, getX(Align.center) + getTextWidth(nickName), getY(Align.top) + NICKNAME_Y_OFFSET);
    }

    private Texture provideTexture(String color, Float radius) {
        return TextureCreator.createCircleTexture(radius.intValue(), color, 3);
    }

    private BitmapFont provideFont() {
        BitmapFont font = FontProvider.provide("monkey", 18, Color.WHITE);
        font.setUseIntegerPositions(true);
        return font;
    }

    private int getTextWidth(String text) {
        return Math.round(text.length() * -3.5f);
    }

}
