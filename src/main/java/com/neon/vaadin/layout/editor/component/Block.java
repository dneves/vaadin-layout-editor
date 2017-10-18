package com.neon.vaadin.layout.editor.component;

import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Block extends VerticalLayout implements LayoutEditorComponent<Block.BlockModel> {

    public static class BlockModel implements EditorComponentModel {
        public final String caption;
        public int nrColumns;

        public BlockModel(String caption) {
            this.caption = caption;
        }

        public BlockModel(String caption, int nrColumns) {
            this.caption = caption;
            this.nrColumns = nrColumns;
        }
    }

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final HorizontalLayout settings = new HorizontalLayout();

    private final Label label = new Label();

    Block() {
        this.setSpacing( true );
        this.addStyleName( "component-block" );

        label.setSizeUndefined();

        HorizontalLayout up = new HorizontalLayout();
        up.setSpacing( true );
        up.setSizeFull();
        up.addComponent( actions );
        up.addComponent( settings );
        up.setComponentAlignment( actions, Alignment.MIDDLE_LEFT );
        up.setComponentAlignment( settings, Alignment.MIDDLE_RIGHT );

        this.addComponent( up );
        this.addComponent( label );
        this.setComponentAlignment( label, Alignment.MIDDLE_CENTER );
    }

    public void addSettings( Component component ) {
        settings.addComponent( component );
    }

    @Override
    public void addAction(Action action) {
        actions.addAction( action );
    }

    @Override
    public void setModel( BlockModel model) {
        label.setValue( model.caption );
    }

    @Override
    public BlockModel getModel() {
        return new BlockModel( label.getValue() );
    }

}
