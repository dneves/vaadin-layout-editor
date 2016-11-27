package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.EditorViewFactory;

public class ColumnsFactory {

    private ColumnsFactory() {

    }

    public static Columns create( EditorViewFactory editorViewFactory ) {
        Columns columns = new Columns( editorViewFactory );
        columns.addStyleName( "component" );
        return columns;
    }

}
