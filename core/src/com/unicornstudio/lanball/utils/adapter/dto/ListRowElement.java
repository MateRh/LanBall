package com.unicornstudio.lanball.utils.adapter.dto;

import lombok.Getter;

@Getter
public class ListRowElement {

    private final String label;

    private final Float width;

    public ListRowElement(String label, Float width) {
        this.label = label;
        this.width = width;
    }

}
