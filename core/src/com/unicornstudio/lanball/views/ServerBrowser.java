package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kotcrab.vis.ui.widget.ListView;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.dto.Host;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;
import com.unicornstudio.lanball.util.adapter.ListAdapter;
import com.unicornstudio.lanball.util.adapter.dto.ListRow;
import com.unicornstudio.lanball.util.adapter.dto.ListRowElement;
import java.util.List;
import java.util.Random;

@Singleton
public class ServerBrowser extends AbstractLmlView {

    private static final String NAME_ROW_LABEL = "name";
    private static final String HOST_ROW_LABEL = "host";
    private static final String PING_ROW_LABEL = "ping";
    private static final float NAME_ROW_WIDTH = 448f;
    private static final float HOST_ROW_WIDTH = 256f;
    private static final float PING_ROW_WIDTH = 48f;

    @Inject
    private ClientService clientService;

    @LmlActor("listViewTable")
    private Table table;

    private ListAdapter<ListRow> adapter;

    public ServerBrowser() {
        super(LanBallGame.newStage());
    }

    @LmlAfter
    private void initialize() {
        adapter = new ListAdapter<>(new Array<>(ListRow.class));
        table.add(new ListView<>(adapter).getMainTable()).grow();
        parseServerListToView(clientService.getServers());
        table.addListener(
                new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        if (event.getListenerActor() == table && getTapCount() > 1) {
                            join();
                        }
                    }
                });
    }

    @Override
    public FileHandle getTemplateFile() {
        return Gdx.files.internal("views/ServerBrowser.lml");
    }

    @Override
    public String getViewId() {
        return "serverBrowser";
    }

    @LmlAction("refresh")
    private void refresh() {
        adapter.clear();
        parseServerListToView(clientService.getServers());
    }

    @LmlAction("join")
    private void join() {
        adapter.getSelection().forEach(
                selection -> {
                    if (clientService.connect(selection.get(HOST_ROW_LABEL).getLabel(), PlayerRole.PLAYER)) {
                        ((LanBallGame) Gdx.app.getApplicationListener()).setView(HostServer.class);
                    }
                }
        );
    }

    private void parseServerListToView(List<Host> servers) {
        servers.forEach(
                server -> {
                    adapter.add(createListRow(server.getName(), server.getAddress(), String.valueOf(new Random().nextInt(5))));
                }
        );
    }

    private ListRow createListRow(String name, String host, String ping) {
        ListRow listRow = new ListRow();
        listRow.put(NAME_ROW_LABEL, new ListRowElement(name, NAME_ROW_WIDTH));
        listRow.put(HOST_ROW_LABEL, new ListRowElement(host, HOST_ROW_WIDTH));
        listRow.put(PING_ROW_LABEL, new ListRowElement(ping, PING_ROW_WIDTH));
        return listRow;
    }
}
