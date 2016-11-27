package com.neon.vaadin.layout.editor.component.model;

import com.neon.dnd.Draggable;

import java.util.List;

public class BlockComponentModel implements EditorComponentModel {

    private List<Draggable> contents;

    public BlockComponentModel() {

    }

    public List<Draggable> getContents() {
        return contents;
    }

    public void setContents(List<Draggable> contents) {
        this.contents = contents;
    }
}
