package com.unicornstudio.lanball.utils.adapter.dto;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ListRow {

    private final Map<String, ListRowElement> elements = new LinkedHashMap<>();

    public void put(String key, ListRowElement element) {
        elements.put(key, element);
    }

    public ListRowElement get(String key) {
        return elements.getOrDefault(key, null);
    }

}
