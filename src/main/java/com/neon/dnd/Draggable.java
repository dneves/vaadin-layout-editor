package com.neon.dnd;

import com.neon.vaadin.layout.editor.IdHolder;
import com.vaadin.ui.Component;

@Deprecated
public interface Draggable< M extends IdHolder > extends Component {

    M getModel();

}
