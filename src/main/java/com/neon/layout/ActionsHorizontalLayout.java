package com.neon.layout;

import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class ActionsHorizontalLayout extends HorizontalLayout {

    public ActionsHorizontalLayout() {
        addStyleName( "actions-container" );
        setSpacing( true );
        setVisible( false );
    }

    public void addAction( Action action, Button.ClickListener listener ) {
        Button button = new Button(action.getCaption(), action.getIcon());
        button.addStyleName( "action" );
        button.setWidth(24, Unit.PIXELS);
        button.setHeight(24, Unit.PIXELS);
        button.addClickListener( listener );
        addComponent( button );
        setVisible( true );
    }

}
