package com.neon.vaadin.layout.editor;

import com.neon.dnd.Draggable;

public interface EditorViewFactory {

    < T extends IdHolder > Draggable< T > create( T model );

}
