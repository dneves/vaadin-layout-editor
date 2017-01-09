package com.neon.vaadin.story;

import com.neon.vaadin.dnd.DraggableComponent;
import com.neon.vaadin.layout.OrderableVerticalLayout;
import com.neon.vaadin.upload.DesktopDropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class Story extends VerticalLayout {

    private final OrderableVerticalLayout container;

    public Story( DesktopDropHandler desktopDropHandler ) {
        container = new OrderableVerticalLayout( "drop here", desktopDropHandler ) {
            @Override
            protected DraggableComponent create( DraggableComponent draggable ) {
                return null;
            }

            @Override
            protected boolean allowRemoveFromSource() {
                return true;
            }
        };
        container.setSpacing( true );

        this.setSpacing( true );
        this.addComponent( container );
        this.addComponent( new StoryItemOptions( container ) );
    }

    public void append( List<Component> inputs, int index ) {
        inputs.forEach( c -> container.handle( c, index ) );
    }

}
