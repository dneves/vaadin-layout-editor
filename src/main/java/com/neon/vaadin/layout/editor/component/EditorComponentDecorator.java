package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.vaadin.event.Action;

import java.util.List;

public class EditorComponentDecorator {

    private final SourceComponentsHolder sourceComponentsHolder;

    public EditorComponentDecorator( SourceComponentsHolder sourceComponentsHolder ) {
        this.sourceComponentsHolder = sourceComponentsHolder;
    }

    public void decorate( LayoutEditorComponent component, OrderableVerticalLayout container ) {
        component.addAction( new Action("X"), event -> {
            List<List<Draggable>> models = component.getModel();

            container.removeComponent( component );

            if ( component.isRemoveFromExternalSource() ) {
                models.forEach(sourceComponentsHolder::give);
            }
        } );
        component.addAction( new Action( "up"), event -> container.moveUp( component ));
        component.addAction( new Action( "down"), event -> container.moveDown( component ));
    }

}
