package com.unicornstudio.lanball;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.EntityType;
import com.unicornstudio.lanball.model.Player;

@Singleton
public class EntitiesService {

    private Map<String, Entity> entities = new HashMap<>();

    public void addEntity(String key, Entity entity) {
        entities.put(key, entity);
    }

    public Entity getEntity(String key) {
        return entities.get(key);
    }

    public void synchronizeEntitiesPosition() {
        entities.values().stream().forEach(this::synchronizeEntityPosition);
    }

    private void synchronizeEntityPosition(Entity entity) {

        Vector2 position = entity.getPhysicsEntity().getBody().getPosition();
        entity.getActor().setPosition(
                position.x - entity.getActor().getWidth()/2,
                position.y - entity.getActor().getHeight()/2);

        if (entity.getType() == EntityType.PLAYER) {
            Player player = (Player) entity;
            player.getSensor().getBody().setTransform(position, 0);
        }
    }

}
