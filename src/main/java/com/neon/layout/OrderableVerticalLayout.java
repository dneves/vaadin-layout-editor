package com.neon.layout;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.dnd.DroppableComponent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class OrderableVerticalLayout< T extends Draggable > extends VerticalLayout {

    private class Spacer extends DroppableComponent< VerticalLayout > {

        Spacer( String label, DropHandler dropHandler) {
            super( new VerticalLayout(), dropHandler );

            Label l = new Label( label );
            l.setSizeUndefined();

            getRoot().setSizeFull();
            getRoot().addComponent( l );
            getRoot().setComponentAlignment( l, Alignment.MIDDLE_CENTER );
        }

    }

    private final DropHandler dropHandler = new OrderableVerticalLayoutDropHandler( this );

    private final Spacer spacer;


    public OrderableVerticalLayout( String dropLabel ) {
        spacer = new Spacer( dropLabel, dropHandler );
        addSpacer();

        this.addComponentAttachListener((ComponentAttachListener) event -> {
            if ( event.getAttachedComponent() != spacer ) {
                removeSpacer();
            }
        });
        this.addComponentDetachListener((ComponentDetachListener) event -> {
            if ( event.getDetachedComponent() != spacer ) {
                if ( getComponentCount() == 0 ) {
                    addSpacer();
                }
            }
        });
    }

    private void addSpacer() {
        super.addComponent( spacer );
    }

    private void removeSpacer() {
        super.removeComponent( spacer );
    }

    public void handle( Component c, int index ) {
        if ( c instanceof DraggableComponent ) {
            DraggableComponent droppableComponent = create( (DraggableComponent) c );
            if ( droppableComponent == null ) {
                return ;
            }

            droppableComponent.setDropHandler( dropHandler );
            this.addComponent( droppableComponent, index );
        }
    }

    protected abstract DraggableComponent< T > create( DraggableComponent< T > draggable );

    public void moveUp( Component c ) {
        int index = getComponentIndex( c );
        if ( index > 0 ) {
            removeComponent( c );
            addComponent( c, index - 1 );
        }
    }

    public void moveDown( Component c ) {
        int index = getComponentIndex( c );
        int componentCount = getComponentCount();
        if ( index < componentCount - 1 ) {
            removeComponent( c );
            addComponent( c, index + 1 );
        }
    }

}
