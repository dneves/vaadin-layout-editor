package com.neon.storyeditor.editor.palette;

import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.vaadin.server.Resource;

import java.util.function.Consumer;

public abstract class StoryEditorPaletteItem< INPUT, OUTPUT > {

    private String label;

    private Resource icon;

    private boolean isVisible;

    public StoryEditorPaletteItem( String label ) {
        this( label, null );
    }

    public StoryEditorPaletteItem( Resource icon ) {
        this( null, icon );
    }

    public StoryEditorPaletteItem( String label, Resource icon ) {
        this.label = label;
        this.icon = icon;
        isVisible = true;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public Resource getIcon() {
        return icon;
    }

    public void setIcon( Resource icon ) {
        this.icon = icon;
    }

    public abstract void createEditorComponent( final Consumer<IStoryEditorComponent< INPUT, OUTPUT >> consume );

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
