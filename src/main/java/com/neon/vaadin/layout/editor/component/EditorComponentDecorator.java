package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.OrderableVerticalLayout;
import com.vaadin.server.ThemeResource;

public class EditorComponentDecorator {

    public EditorComponentDecorator( ) {

    }

    public void decorate( LayoutEditorComponent component, OrderableVerticalLayout container ) {
        component.addAction( new Action("", new ThemeResource( "icons/cancel-12x12.png" ), () -> {
            container.removeComponent( component );
        } ) );
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-up-12x12.png" ), () -> container.moveUp( component ) ) );
        component.addAction( new Action( "", new ThemeResource( "icons/arrow-down-12x12.png" ), () -> container.moveDown( component ) ) );
    }

}
