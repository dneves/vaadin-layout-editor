package com.neon.storyeditor;

import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * Componente que faz wrap a um componente arbitrário e torna-o draggable.
 * Permite o acesso ao componente pai, mesmo que o utilizador tenha arrastado um componente
 * filho deste.
 *
 * @param <M> Classe do Component a fazer wrap
 */
public class DraggableComponent<M extends Component> extends DragAndDropWrapper {

    private static final long serialVersionUID = 8482991688095679419L;
    private final M mainComponent;

    public DraggableComponent(M component) {
        this(component, component);
    }

    private DraggableComponent(Component root, M mainComponent) {
        super(root);
        this.mainComponent = mainComponent;
        this.setDragStartMode(DragStartMode.WRAPPER);
    }

    public M getMainComponent() {
        return mainComponent;
    }

}
