package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.EditorViewFactory;

public class BlockFactory {

    private BlockFactory() {

    }

    public static Block create( EditorViewFactory editorViewFactory ) {
        Block block = new Block( editorViewFactory );
        block.addStyleName( "component" );
        return block;
    }

}
