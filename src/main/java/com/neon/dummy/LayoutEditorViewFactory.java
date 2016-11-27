package com.neon.dummy;

import com.neon.dnd.Draggable;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.IdHolder;

public class LayoutEditorViewFactory implements EditorViewFactory {

    public LayoutEditorViewFactory() {

    }

    @Override
    public < T extends IdHolder> Draggable create(T model ) {
        if ( model instanceof Content ) {
            return new ContentView( ( Content ) model );
        }
        return null;
    }

}
