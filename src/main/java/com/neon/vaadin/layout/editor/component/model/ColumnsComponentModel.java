package com.neon.vaadin.layout.editor.component.model;

public class ColumnsComponentModel implements EditorComponentModel {

    private Object sizes;

    private BlockComponentModel column1;
    private BlockComponentModel column2;

    public ColumnsComponentModel() {

    }

    public Object getSizes() {
        return sizes;
    }

    public void setSizes(Object sizes) {
        this.sizes = sizes;
    }

    public BlockComponentModel getColumn1() {
        return column1;
    }

    public void setColumn1(BlockComponentModel column1) {
        this.column1 = column1;
    }

    public BlockComponentModel getColumn2() {
        return column2;
    }

    public void setColumn2(BlockComponentModel column2) {
        this.column2 = column2;
    }
}
