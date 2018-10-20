package com.unicornstudio.lanball.video;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.unicornstudio.lanball.map.settings.Team;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.utils.TextureCreator;
import lombok.Setter;

@Setter
public class ContestantActor extends Actor {

    private final static int NICKNAME_X_OFFSET = -10;
    private final static int NICKNAME_Y_OFFSET = 15;

    private final Texture texture;

    private final BitmapFont nickNameFont;

    private final String nickName;

    public ContestantActor(Team team, String name, Float radius) {
        setWidth(radius);
        setHeight(radius);
        texture = provideTexture(team.getColor(), radius);
        nickNameFont = provideFont();
        nickName = name;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
        nickNameFont.draw(batch, nickName, getX(Align.left) + NICKNAME_X_OFFSET, getY(Align.top) + NICKNAME_Y_OFFSET);
    }

    private Texture provideTexture(String color, Float radius) {
        return TextureCreator.createCircleTexture(radius.intValue(), color);
    }

    private BitmapFont provideFont() {
        BitmapFont font = new BitmapFont();
        font.setUseIntegerPositions(true);
        return font;
    }
}
