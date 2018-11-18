package com.unicornstudio.lanball.utils.adapter;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.VisTable;
import com.unicornstudio.lanball.utils.adapter.dto.ListRow;
import com.unicornstudio.lanball.utils.adapter.dto.ListRowElement;

public class ListAdapter<ItemT> extends SimpleListAdapter<ItemT> {

    private final static int LABEL_HEIGHT = 32;

    public ListAdapter(Array<ItemT> array) {
        super(array);
        setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    protected VisTable createView (ItemT item) {
        VisTable table = new VisTable();
        ListRow row = (ListRow) item;
        for (ListRowElement element: row.getElements().values()) {
            table.add(element.getLabel())
                    .expand(element.getWidth().intValue(), LABEL_HEIGHT);
        }
        return table;
    }

}
