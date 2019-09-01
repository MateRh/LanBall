package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.unicornstudio.lanball.builder.FontBuilder;
import com.unicornstudio.lanball.model.map.settings.Team;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class ContestantActor extends Actor {

    private final static BitmapFont font = new FontBuilder()
            .name("Gravity-Book")
            .size(14)
            .renderCount(6)
            .color(Color.WHITE)
            .shadowOffsetX(1)
            .shadowOffsetY(1)
            .build();

    private final Texture texture;

    private final String nickName;

    public ContestantActor(Team team, String name, Float radius) {
        setWidth(radius);
        setHeight(radius);
        setColor(Color.valueOf(team.getColor()));
        texture = new Texture(new Pixmap(Gdx.files.internal("images/player.png")), true);
        nickName = StringUtils.truncate(name, 12);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        font.draw(batch, nickName, getX(Align.center) - getTextWidth(nickName),getY(Align.center) - font.getLineHeight() * 1.25f);
        super.draw(batch, parentAlpha);
    }

    private int getTextWidth(String text) {
        return Math.round(text.length() * font.getSpaceXadvance() * 1.25f);
    }

}
