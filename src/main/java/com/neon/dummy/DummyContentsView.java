package com.neon.dummy;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.vaadin.layout.editor.IdHolder;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class DummyContentsView extends VerticalLayout implements SourceComponentsHolder {

    private final DummyContentProvider dummyContentProvider = new DummyContentProvider();

    public DummyContentsView() {
        setCaption( "lista de conteudos" );
        setSpacing( true );

        List<Content> contents = dummyContentProvider.get();
        for (Content content : contents) {

            DraggableComponent< Content > draggableComponent = new DraggableComponent<>( new ContentView( content ) );
            draggableComponent.setSizeUndefined();

            addComponent( draggableComponent );
        }
    }

    @Override
    public void give( List< Draggable > draggables ) {
        draggables.forEach( this::add );
    }

    private void add( Draggable draggable ) {
        IdHolder model = draggable.getModel();
        if ( model instanceof Content ) {
            DraggableComponent< Content > draggableComponent = new DraggableComponent<>( new ContentView( ( Content ) model) );
            draggableComponent.setSizeUndefined();
            addComponent( draggableComponent );
        }
    }

}
