package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;

public class BlockFactory {

    private BlockFactory() {

    }

    public static Block create( EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder,
                                boolean removeFromExternalSource ) {
        Block block = new Block(editorViewFactory, sourceComponentsHolder);
        block.addStyleName( "component" );
        block.setRemoveFromExternalSource( removeFromExternalSource );
        return block;
    }

}
