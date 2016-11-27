package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

public interface LayoutEditorComponent< M extends EditorComponentModel > extends Component {

    void addAction(Action action, Button.ClickListener listener );

    void setModel( M model );

    M getModel();

    boolean isRemoveFromExternalSource();

}
