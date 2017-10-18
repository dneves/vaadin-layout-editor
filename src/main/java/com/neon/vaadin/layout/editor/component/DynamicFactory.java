package com.neon.vaadin.layout.editor.component;

public class DynamicFactory {

    private DynamicFactory() {

    }

    public static DynamicBlock create( int maxBlocks ) {
        DynamicBlock dynamicBlock = new DynamicBlock( maxBlocks );
        dynamicBlock.addStyleName( "component" );
        return dynamicBlock;
    }

}
