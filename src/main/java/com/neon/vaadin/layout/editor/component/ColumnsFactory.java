package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;

public class ColumnsFactory {

    private ColumnsFactory() {

    }

    public static Columns create(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        Columns columns = new Columns(editorViewFactory, sourceComponentsHolder);
        columns.addStyleName( "component" );
        return columns;
    }

}
