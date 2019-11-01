package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Inject;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.common.Ports;
import com.unicornstudio.lanball.network.server.ServerService;
import com.unicornstudio.lanball.service.StageService;

import javax.inject.Singleton;

@Singleton
public class Menu extends AbstractLmlView {

    @Inject
    private ServerService serverService;

    @Inject
    private ClientService clientService;

    @LmlActor("hostServerButton")
    private TextButton hostServerButton;

    @Inject
    public Menu(StageService stageService) {
        super(stageService.getStage(true));
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/Menu.lml");
    }

    @Override
    public String getViewId() {
        return "menu";
    }

    @LmlAfter
    private void initialize() {
        hostServerButton.addListener(createChangeListener());
    }

    private ChangeListener createChangeListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Dialog dialog = UserInterfaceUtils.createLoadingDialog().show(getStage());
                for (Integer port: Ports.getList()) {
                    if (clientService.isPortOpen(port)) {
                        if (serverService.start(port)) {
                            //dialog.remove();
                            ((LanBallGame) Gdx.app.getApplicationListener()).setView(HostServer.class);
                        }
                        break;
                    }
                }
            }
        };
    }

}
