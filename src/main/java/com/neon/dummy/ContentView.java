package com.neon.dummy;

import com.neon.dnd.Draggable;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ContentView extends VerticalLayout implements Draggable< Content > {

    private final Content content;

    public ContentView(Content content) {
        this.content = content;
        addComponent( new Label( content.getId() ) );
    }

    @Override
    public Content getModel() {
        return content;
    }

}
