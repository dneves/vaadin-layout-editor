package com.neon.dnd;

import com.vaadin.ui.DragAndDropWrapper;

public class DraggableComponent< M > extends DragAndDropWrapper {

    private final Draggable< M > root;

    public DraggableComponent( Draggable< M > root ) {
        super(root);
        this.root = root;
        this.setDragStartMode( DragStartMode.WRAPPER );
    }

    public Draggable< M > getRoot() {
        return root;
    }

}
