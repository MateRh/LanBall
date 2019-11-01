package com.unicornstudio.lanball.util.adapter.dto;

import com.unicornstudio.lanball.model.map.elements.MapElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditorElementListRow {

    private int position;

    private EditorElement editorElement;

    private MapElement mapElement;

}
