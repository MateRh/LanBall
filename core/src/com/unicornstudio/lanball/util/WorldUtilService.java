package com.unicornstudio.lanball.util;

import com.badlogic.gdx.math.Vector2;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.core.Screen;
import com.unicornstudio.lanball.model.map.MapService;
import com.unicornstudio.lanball.model.map.settings.BallSettings;
import com.unicornstudio.lanball.model.map.settings.Position;
import com.unicornstudio.lanball.model.TeamType;

@Singleton
public class WorldUtilService {

    @Inject
    private MapService mapService;

    public Vector2 calcPosition(TeamType teamType, int positionId) {
        BallSettings ballSettings = mapService.getMapSettings().getBallSettings();
        float x = ballSettings.getPositionX() / Screen.getPixelPerMeter();
        float y = ballSettings.getPositionY() / Screen.getPixelPerMeter();
        Position position = mapService.getMapSettings().getTeams().get(teamType.getType()).getPositions().get(positionId);
        if (position == null) {
            return null;
        }
        return getForceFromAngle(
                position.getAngle(),
                position.getDistance() * Screen.getMeterPerPixel())
                .add(x, y);
    }

    private static Vector2 getForceFromAngle(float angle, float distance) {
        double a = Math.toRadians(90 - angle);
        double dx = Math.cos(a);
        double dy = Math.sin(a);
        return new Vector2((float) dx, (float) dy).scl(distance);
    }

}
