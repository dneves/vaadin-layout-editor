package com.neon.vaadin.layout.editor.component.model;

import java.util.List;

public class DynamicBlockModel implements EditorComponentModel {

    private List< BlockComponentModel > blocks;

    public DynamicBlockModel() {

    }

    public List<BlockComponentModel> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockComponentModel> blocks) {
        this.blocks = blocks;
    }
}
