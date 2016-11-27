package com.neon.vaadin.layout.editor.component;

import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.ColumnsComponentModel;
import com.vaadin.event.Action;

public class EditorComponentDecorator {

    private final SourceComponentsHolder sourceComponentsHolder;

    public EditorComponentDecorator( SourceComponentsHolder sourceComponentsHolder ) {
        this.sourceComponentsHolder = sourceComponentsHolder;
    }

    public void decorate( Block component, OrderableVerticalLayout container ) {
        component.addAction( new Action("X"), event -> {
            BlockComponentModel model = component.getModel();

            container.removeComponent( component );

            if ( component.isRemoveFromExternalSource() ) {
                sourceComponentsHolder.give( model.getContents() );
            }
        } );
        component.addAction( new Action( "up"), event -> container.moveUp( component ));
        component.addAction( new Action( "down"), event -> container.moveDown( component ));
    }

    public void decorate( Columns component, OrderableVerticalLayout container ) {
        component.addAction( new Action("X"), event -> {
            ColumnsComponentModel model = component.getModel();

            container.removeComponent( component );

            if ( component.isRemoveFromExternalSource() ) {
                sourceComponentsHolder.give( model.getColumn1().getContents() );
                sourceComponentsHolder.give( model.getColumn2().getContents() );
            }
        } );
        component.addAction( new Action( "up"), event -> container.moveUp( component ));
        component.addAction( new Action( "down"), event -> container.moveDown( component ));
    }

}
