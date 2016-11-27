package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block extends VerticalLayout implements LayoutEditorComponent<BlockComponentModel> {

    private final EditorViewFactory editorViewFactory;

    private final OrderableVerticalLayout contents = new OrderableVerticalLayout( "arraste para aqui" ) {
        @Override
        protected DraggableComponent create( DraggableComponent draggableComponent) {
            Draggable draggable = draggableComponent.getRoot();
            return Block.this.create( draggable );
        }
    };

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final SourceComponentsHolder sourceComponentsHolder;


    Block(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        this.editorViewFactory = editorViewFactory;
        this.sourceComponentsHolder = sourceComponentsHolder;

        setSpacing( true );

        addComponent( actions );
        addComponent( contents );

        contents.setSpacing( true );
        contents.addStyleName( "component-block-contents" );

        addStyleName( "component-block" );
    }

    @Override
    public boolean isRemoveFromExternalSource() {
        return contents.isRemoveFromExternalSource();
    }

    void setRemoveFromExternalSource(boolean removeFromExternalSource) {
        contents.setRemoveFromExternalSource( removeFromExternalSource );
    }


    @Override
    public void addAction( Action action, Button.ClickListener listener ) {
        actions.addAction( action, listener );
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

        elementWrapper.addAction( new Action( "X" ), event -> {
            Block.this.contents.removeComponent( cDraggableComponent );
            sourceComponentsHolder.give(Collections.singletonList(draggable));
        } );

        return cDraggableComponent;
    }

}
