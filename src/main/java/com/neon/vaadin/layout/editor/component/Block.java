package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class Block extends VerticalLayout implements LayoutEditorComponent<BlockComponentModel> {

    private final EditorViewFactory editorViewFactory;

    private final OrderableVerticalLayout contents = new OrderableVerticalLayout( "arraste para aqui" ) {
//        @Override
//        protected DraggableComponent create( DraggableComponent draggableComponent) {
//            Draggable draggable = draggableComponent.getRoot();
//            return Block.this.create( draggable );
//        }


        @Override
        protected com.neon.vaadin.dnd.DraggableComponent create(com.neon.vaadin.dnd.DraggableComponent draggable) {
            return null;
        }

        @Override
        protected boolean allowRemoveFromSource() {
            return true;
        }
    };

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();


    Block(EditorViewFactory editorViewFactory ) {
        this.editorViewFactory = editorViewFactory;

        setSpacing( true );

        addComponent( actions );
        addComponent( contents );

        contents.setSpacing( true );
        contents.addStyleName( "component-block-contents" );

        addStyleName( "component-block" );
    }

    @Override
    public void addAction(com.neon.vaadin.layout.editor.Action action) {
        actions.addAction( action );
    }

    @Override
    public void setModel(BlockComponentModel model) {
        List<Draggable> contents = model.getContents();
        contents.forEach( d -> {
            DraggableComponent component = create( d );
            this.contents.handle( component, this.contents.getComponentCount() );
        });
    }

    @Override
    public BlockComponentModel getModel() {
        List< Draggable > result = new ArrayList<>( contents.getComponentCount() );

        for (Component component : contents) {
            if ( component instanceof DraggableComponent ) {
                result.add( ((DraggableComponent) component).getRoot() );
            }
        }

        BlockComponentModel model = new BlockComponentModel();
        model.setContents( result );
        return model;
    }

    private DraggableComponent create(Draggable draggable ) {
        Draggable component = editorViewFactory.create(draggable.getModel());
        if ( component == null ) {
            return null;
        }

        BlockElementWrapper elementWrapper = new BlockElementWrapper(component);

        DraggableComponent cDraggableComponent = new DraggableComponent( elementWrapper );

        elementWrapper.addAction( new Action( "", new ThemeResource( "icons/cancel-12x12.png" ), () -> {
            Block.this.contents.removeComponent( cDraggableComponent );
        } ) );

        return cDraggableComponent;
    }

}
