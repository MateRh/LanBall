package com.unicornstudio.lanball.network.client;


import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.map.MapService;
import com.unicornstudio.lanball.model.Contestant;
import com.unicornstudio.lanball.network.protocol.request.CreateRemotePlayerNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.MotionUpdateNetworkObject;

@Singleton
public class ClientListener extends Listener {

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private MapService mapService;

    public void received (Connection connection, Object object) {
        if (object instanceof CreateRemotePlayerNetworkObject) {
            CreateRemotePlayerNetworkObject request = (CreateRemotePlayerNetworkObject) object;
            System.out.println(request.getName());
            System.out.println(request.getTeamType());
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    entitiesService.createContestant(connection.getID(), request.getName(), mapService.getMap().getSettings().getTeams().get(0));
                }
            });
        }
        if (object instanceof MotionUpdateNetworkObject) {
            MotionUpdateNetworkObject request = (MotionUpdateNetworkObject) object;
            Contestant contestant = (Contestant) entitiesService.getEntity("contestant_" + connection.getID());
            if (contestant != null) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(request.getPositionY());
                        contestant.getPhysicsEntity().getBody().setTransform(request.getPositionX(), request.getPositionY(), 0f);
                        contestant.getPhysicsEntity().getBody().setLinearVelocity(request.getVelocityX(), request.getVelocityY());
                    }
                });
            }
        }
    }

}
