package com.neon.vaadin.layout.editor.component;

import com.neon.layout.OrderableVerticalLayout;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.ColumnsComponentModel;
import com.vaadin.event.Action;
import com.vaadin.server.ThemeResource;

public class EditorComponentDecorator {

    public EditorComponentDecorator( ) {

    }

    public void decorate( Block component, OrderableVerticalLayout container ) {
        component.addAction( new Action("", new ThemeResource( "icons/cancel-12x12.png" )), event -> {
            BlockComponentModel model = component.getModel();

            container.removeComponent( component );
        } );
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-up-12x12.png" ) ), event -> container.moveUp( component ));
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-down-12x12.png" ) ), event -> container.moveDown( component ));
    }

    public void decorate( Columns component, OrderableVerticalLayout container ) {
        component.addAction( new Action("", new ThemeResource( "icons/cancel-12x12.png" )), event -> {
            ColumnsComponentModel model = component.getModel();

            container.removeComponent( component );
        } );
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-up-12x12.png" ) ), event -> container.moveUp( component ));
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-down-12x12.png" ) ), event -> container.moveDown( component ));
    }

}
