package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.vaadin.ui.Component;

public interface LayoutEditorComponent< M extends EditorComponentModel > extends Component {

    void addAction( Action action );

    void setModel( M model );

    M getModel();

}
