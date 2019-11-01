package com.unicornstudio.lanball.util.adapter;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.VisTable;
import com.unicornstudio.lanball.util.adapter.dto.EditorElementListRow;

public class EditorElementsListAdapter<ItemT> extends SimpleListAdapter<ItemT> {

    public EditorElementsListAdapter(Array<ItemT> array) {
        super(array);
        setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    protected VisTable createView (ItemT item) {
        VisTable table = new VisTable();
        EditorElementListRow row = (EditorElementListRow) item;
        table.add(String.valueOf(row.getPosition()));
        table.add(row.getEditorElement().getName()).expand();
        return table;
    }

}
