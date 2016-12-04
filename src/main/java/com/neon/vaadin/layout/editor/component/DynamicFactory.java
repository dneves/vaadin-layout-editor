package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.EditorViewFactory;

public class DynamicFactory {

    private DynamicFactory() {

    }

    public static DynamicBlock create( EditorViewFactory editorViewFactory ) {
        DynamicBlock dynamicBlock = new DynamicBlock( editorViewFactory );
        dynamicBlock.addStyleName( "component" );
        return dynamicBlock;
    }

}
