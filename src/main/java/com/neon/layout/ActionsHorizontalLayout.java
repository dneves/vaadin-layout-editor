package com.neon.layout;

import com.neon.vaadin.layout.editor.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class ActionsHorizontalLayout extends HorizontalLayout {
    
    public ActionsHorizontalLayout() {
        addStyleName( "actions-container" );
        setSpacing( true );
        setVisible( false );
    }

    public void addAction( Action action ) {
        Button button = new Button(action.label, action.icon);
        button.addStyleName( "action" );
        button.setWidth(24, Unit.PIXELS);
        button.setHeight(24, Unit.PIXELS);
        button.addClickListener( event -> {
            if ( action.listener != null ) {
                action.listener.run();
            }
        } );
        button.setVisible( action.isVisible );
        addComponent( button );
        setVisible( true );
    }

}
