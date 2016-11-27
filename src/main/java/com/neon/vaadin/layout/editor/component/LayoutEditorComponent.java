package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.List;

public interface LayoutEditorComponent extends Component {

    void addAction(Action action, Button.ClickListener listener );

    void setModel( List< List< Draggable > > draggables );

    List< List< Draggable > > getModel();

    boolean isRemoveFromExternalSource();

}
