package com.neon.vaadin.layout.editor;

import com.neon.dnd.DraggableComponent;
import com.neon.vaadin.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.component.DynamicBlock;
import com.neon.vaadin.layout.editor.component.DynamicFactory;
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

    private final OrderableVerticalLayout layout = new OrderableVerticalLayout( "adicione componentes" ) {
        @Override
        protected com.neon.vaadin.dnd.DraggableComponent create(com.neon.vaadin.dnd.DraggableComponent draggable) {
            return null;
        }

        @Override
        protected boolean allowRemoveFromSource() {
            return false;
        }
    };

    private final Button buttonDynamic = new Button( "Bloco", event -> addDynamic() );


    private final EditorViewFactory editorViewFactory;

    private final EditorComponentDecorator editorComponentDecorator;


    public LayoutEditor( EditorViewFactory editorViewFactory ) {
        this.editorViewFactory = editorViewFactory;

        this.editorComponentDecorator = new EditorComponentDecorator();

        setCaption( "Layout");

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
        actions.addComponent( buttonDynamic );
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

    private void addDynamic() {
        DynamicBlock dynamicBlock = DynamicFactory.create(editorViewFactory);

        editorComponentDecorator.decorate( dynamicBlock, layout );

        layout.addComponent( dynamicBlock );
    }
}
