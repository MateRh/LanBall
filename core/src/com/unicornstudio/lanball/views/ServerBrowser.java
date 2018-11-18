package com.unicornstudio.lanball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.annotation.LmlAfter;
import com.github.czyzby.lml.parser.impl.AbstractLmlView;
import com.google.inject.Inject;
import com.kotcrab.vis.ui.widget.ListView;
import com.unicornstudio.lanball.LanBallGame;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.utils.adapter.ListAdapter;
import com.unicornstudio.lanball.utils.adapter.dto.ListRow;
import com.unicornstudio.lanball.utils.adapter.dto.ListRowElement;
import java.util.List;

public class ServerBrowser extends AbstractLmlView {

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
                selection -> clientService.connect(selection.get("host").getLabel())
        );
    }

    private void parseServerListToView(List<String> servers) {
        servers.forEach(
                server -> {
                    adapter.add(createListRow("localhost", server, "1"));
                }
        );
    }

    private ListRow createListRow(String name, String host, String ping) {
        ListRow listRow = new ListRow();
        listRow.put("name", new ListRowElement(name, 448f));
        listRow.put("host", new ListRowElement(host, 256f));
        listRow.put("ping", new ListRowElement(ping, 48f));
        return listRow;
    }

}
