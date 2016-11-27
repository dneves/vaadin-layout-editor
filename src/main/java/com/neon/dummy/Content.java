package com.neon.dummy;

import com.neon.vaadin.layout.editor.IdHolder;

public class Content implements IdHolder {

    private String id;

    public Content(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
