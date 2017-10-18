package com.neon.vaadin.layout.editor.component.model;

import com.neon.vaadin.layout.editor.component.Block;

import java.util.List;

public class DynamicBlockModel implements EditorComponentModel {

    private List<Block.BlockModel> blocks;

    public DynamicBlockModel() {

    }

    public List<Block.BlockModel> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block.BlockModel> blocks) {
        this.blocks = blocks;
    }
}
