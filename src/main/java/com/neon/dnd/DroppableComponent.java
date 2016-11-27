package com.neon.dnd;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

public class DroppableComponent< T extends Component > extends DragAndDropWrapper {

    private final T root;

    public DroppableComponent( T root ) {
        this( root, null );
    }

    public DroppableComponent(T root, DropHandler dropHandler ) {
        super(root);
        this.root = root;
        this.setDropHandler( dropHandler );
    }

    public T getRoot() {
        return root;
    }

}
