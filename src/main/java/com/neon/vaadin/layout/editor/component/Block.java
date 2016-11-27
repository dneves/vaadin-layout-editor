package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block extends VerticalLayout implements LayoutEditorComponent {

    private final EditorViewFactory editorViewFactory;

    private final OrderableVerticalLayout< Draggable > contents = new OrderableVerticalLayout< Draggable >( "arraste para aqui" ) {
        @Override
        protected DraggableComponent< Draggable > create( DraggableComponent< Draggable > draggableComponent) {
            Draggable draggable = draggableComponent.getRoot();
            return Block.this.create( draggable );
        }
    };

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final SourceComponentsHolder sourceComponentsHolder;


    protected Block(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        this.editorViewFactory = editorViewFactory;
        this.sourceComponentsHolder = sourceComponentsHolder;

        setSpacing( true );

        addComponent( actions );
        addComponent( contents );

        contents.addStyleName( "component-block-contents" );

        addStyleName( "component-block" );
    }

    @Override
    public void addAction( Action action, Button.ClickListener listener ) {
        actions.addAction( action, listener );
    }

    @Override
    public List< List< Draggable > > getModel() {
        List< Draggable > result = new ArrayList<>( contents.getComponentCount() );

        for (Component component : contents) {
            if ( component instanceof DraggableComponent ) {
                result.add( ((DraggableComponent) component).getRoot() );
            }
        }

        return Collections.singletonList(result);
    }

    @Override
    public void setModel( List< List<Draggable> > draggables) {
        draggables.forEach( ds -> {
            ds.forEach( d -> {
                DraggableComponent<Draggable> component = create( d );
                contents.handle( component, contents.getComponentCount() );
            } );
        } );
    }

    private DraggableComponent< Draggable > create( Draggable draggable ) {
        Draggable component = editorViewFactory.create(draggable.getModel());
        if ( component == null ) {
            return null;
        }

        BlockElementWrapper elementWrapper = new BlockElementWrapper(component);

        DraggableComponent< Draggable > cDraggableComponent = new DraggableComponent<>( elementWrapper );

        elementWrapper.addAction( new Action( "X" ), event -> {
            Block.this.contents.removeComponent( cDraggableComponent );
            sourceComponentsHolder.give(Collections.singletonList(draggable));
        } );

        return cDraggableComponent;
    }

}
