package com.neon.storyeditor;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

public class DroppableComponent<M extends Component> extends DragAndDropWrapper {
    private static final long serialVersionUID = 1719939741483234386L;

    public DroppableComponent(M root) {
        super(root);
    }

    public DroppableComponent(M root, DropHandler dropHandler) {
        super(root);
        setDropHandler(dropHandler);
    }

    public M getRoot() {
        return (M) getCompositionRoot();
    }
}

