package com.unicornstudio.lanball.util.adapter;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.VisTable;
import com.unicornstudio.lanball.util.adapter.dto.EditorElement;
import com.unicornstudio.lanball.util.adapter.dto.ListRow;
import com.unicornstudio.lanball.util.adapter.dto.ListRowElement;

public class EditorElementTypesListAdapter<ItemT> extends SimpleListAdapter<ItemT> {

    public EditorElementTypesListAdapter(Array<ItemT> array) {
        super(array);
        setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    protected VisTable createView (ItemT item) {
        VisTable table = new VisTable();
        EditorElement row = (EditorElement) item;
        table.add(row.getName()).expand();
        return table;
    }

}
