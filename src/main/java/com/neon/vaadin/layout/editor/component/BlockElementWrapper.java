package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.IdHolder;
import com.vaadin.ui.VerticalLayout;

public class BlockElementWrapper extends VerticalLayout implements Draggable {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final Draggable draggable;


    public BlockElementWrapper( Draggable draggable ) {
        this.draggable = draggable;
        addStyleName( "block-element-container" );

        setSpacing( true );
        setMargin( true );

        addComponent( actions );
        addComponent( draggable );
    }

    public void addAction( Action action ) {
        actions.addAction( action );
    }

    @Override
    public IdHolder getModel() {
        return draggable.getModel();
    }

}
