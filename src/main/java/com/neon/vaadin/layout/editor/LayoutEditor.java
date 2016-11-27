package com.neon.vaadin.layout.editor;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.component.Block;
import com.neon.vaadin.layout.editor.component.BlockFactory;
import com.neon.vaadin.layout.editor.component.Columns;
import com.neon.vaadin.layout.editor.component.ColumnsFactory;
import com.neon.vaadin.layout.editor.component.EditorComponentDecorator;
import com.neon.vaadin.layout.editor.component.LayoutEditorComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class LayoutEditor extends VerticalLayout {

    private final OrderableVerticalLayout<Draggable> layout = new OrderableVerticalLayout< Draggable>( "adicione componentes" ) {
        @Override
        protected DraggableComponent< Draggable > create( DraggableComponent< Draggable > draggable) {
            return null;
        }
    };

    private final Button buttonBlock = new Button( "Bloco", event -> addBlock());

    private final Button buttonColumns = new Button( "Colunas", event -> addColumns());


    private final EditorViewFactory editorViewFactory;

    private final SourceComponentsHolder sourceComponentsHolder;

    private final EditorComponentDecorator editorComponentDecorator;


    public LayoutEditor( EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        this.editorViewFactory = editorViewFactory;
        this.sourceComponentsHolder = sourceComponentsHolder;

        this.editorComponentDecorator = new EditorComponentDecorator(sourceComponentsHolder);

        this.addStyleName( "layout-editor" );
        this.setSpacing( true );

        layout.addStyleName( "component-container" );
        layout.setSpacing( true );
        layout.setMargin( true );

        Panel layoutContainer = new Panel( layout );
        this.addComponent( layoutContainer );

        HorizontalLayout actions = new HorizontalLayout();
        actions.setSpacing( true );
        actions.addStyleName( "editor-actions-container" );
        actions.addComponent( buttonBlock );
        actions.addComponent( buttonColumns );
        this.addComponent( actions );
    }

    public void setModel(List<LayoutEditorComponent> components) {
        components.forEach( component -> {
            editorComponentDecorator.decorate( component, layout );
            layout.addComponent( component );
        } );
    }

    public List< LayoutEditorComponent > getModel() {
        List< LayoutEditorComponent > model = new ArrayList<>( layout.getComponentCount() );

        for (Component component : layout) {
            if ( component instanceof LayoutEditorComponent ) {
                model.add((LayoutEditorComponent) component);
            }
        }

        return model;
    }

    private void addBlock() {
        Block block = BlockFactory.create(editorViewFactory, sourceComponentsHolder);

        editorComponentDecorator.decorate( block, layout );

        layout.addComponent( block );
    }

    private void addColumns() {
        Columns columns = ColumnsFactory.create( editorViewFactory, sourceComponentsHolder );

        editorComponentDecorator.decorate( columns, layout );

        layout.addComponent( columns );
    }

}
