package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kotcrab.vis.ui.layout.DragPane;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.request.GetPlayersListNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamNetworkObject;

import java.util.List;

@Singleton
public class HostServer extends AbstractLmlView {

    @Inject
    private ClientService clientService;

    @LmlActor("teamOneDragPane")
    private DragPane teamOneDragPane;

    @LmlActor("teamSpectatorsDragPane")
    private DragPane teamSpectatorsDragPane;

    @LmlActor("teamTwoDragPane")
    private DragPane teamTwoDragPane;

    @LmlActor("timeLimitSelectBox")
    private SelectBox<Label> timeLimitSelectBox;

    @LmlActor("scoreLimitSelectBox")
    private SelectBox<Label> scoreLimitSelectBox;

    @LmlActor("pickMapButton")
    private Button pickMapButton;

    @LmlActor("startTheGameButton")
    private Button startTheGameButton;

    public HostServer() {
        super(LanBallGame.newStage());
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/HostServer.lml");
    }

    @Override
    public String getViewId() {
        return "hostServer";
    }

    @LmlAfter
    private void initialize() {
        sendGetPlayersRequest();
        teamOneDragPane.setListener(createDragPaneListener());
    }

    private void sendGetPlayersRequest() {
        clientService.sendRequest(new GetPlayersListNetworkObject());
    }

    public void updateTeamsDragPanels(List<PlayerDto> playerDtoList) {
        teamOneDragPane.clear();
        teamTwoDragPane.clear();
        teamSpectatorsDragPane.clear();
        playerDtoList.
                forEach(
                        playerDto ->
                        {
                            switch (playerDto.getType()) {
                                case TEAM1:
                                    teamOneDragPane.addActor(new VisLabel(playerDto.getName()));
                                    break;
                                case TEAM2:
                                    teamTwoDragPane.addActor(new VisLabel(playerDto.getName()));
                                    break;
                                case SPECTATORS:
                                    teamSpectatorsDragPane.addActor(new VisLabel(playerDto.getName()));
                                    break;
                            }
                        }
                );
    }

    private DragPane.DragPaneListener createDragPaneListener() {
        return new DragPane.DragPaneListener() {
            @Override
            public boolean accept(DragPane dragPane, Actor actor) {
                clientService.sendRequest(
                        new PlayerChangeTeamNetworkObject(((Label) actor).getText().toString(),
                                getTeamTypeByDragPane(dragPane)));
                return true;
            }
        };
    }

    private TeamType getTeamTypeByDragPane(DragPane dragPane) {
        if (dragPane == teamOneDragPane) {
            return TeamType.TEAM1;
        } else if (dragPane == teamTwoDragPane) {
            return TeamType.TEAM2;
        } else {
            return TeamType.SPECTATORS;
        }
    }

}
