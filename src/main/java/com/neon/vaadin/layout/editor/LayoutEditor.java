package com.neon.vaadin.layout.editor;

import com.neon.dnd.Draggable;
import com.neon.dnd.DraggableComponent;
import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.component.Block;
import com.neon.vaadin.layout.editor.component.Columns;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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


    public LayoutEditor( EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        this.editorViewFactory = editorViewFactory;
        this.sourceComponentsHolder = sourceComponentsHolder;

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

    private void addBlock() {
        Block block = new Block(editorViewFactory, sourceComponentsHolder);
        block.addStyleName( "component" );

        block.addAction( new Action("X"), event -> {
            List<Draggable> models = block.getModel();

            layout.removeComponent( block );

            sourceComponentsHolder.give( models );
        } );
        block.addAction( new Action( "up"), event -> layout.moveUp( block ));
        block.addAction( new Action( "down"), event -> layout.moveDown( block ));

        layout.addComponent( block );
    }

    private void addColumns() {



        Columns columns = new Columns(editorViewFactory, sourceComponentsHolder);
        columns.addStyleName( "component" );

        columns.addAction( new Action("X"), event -> {
            List< Draggable > models = columns.getModel();

            layout.removeComponent( columns );

            sourceComponentsHolder.give( models );
        } );
        columns.addAction( new Action( "up"), event -> layout.moveUp( columns ) );
        columns.addAction( new Action( "down"), event -> layout.moveDown( columns ) );

        layout.addComponent( columns );
    }

}
