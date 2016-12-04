package com.neon.storyeditor.editor.action;

import com.vaadin.server.Resource;

import java.io.Serializable;

public abstract class StoryComponentAction implements Runnable, Serializable {

    private static final long serialVersionUID = -6008721696846148899L;
    private String label;

    private String description;

    private Resource icon;

    private boolean visible = true;

    public StoryComponentAction( String label ) {
        this.label = label;
    }

    public StoryComponentAction( Resource icon ) {
        this.icon = icon;
    }

    public StoryComponentAction( String label, Resource icon ) {
        this.label = label;
        this.icon = icon;
    }

    public StoryComponentAction( String label, String description, Resource icon ) {
        this.label = label;
        this.description = description;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public Resource getIcon() {
        return icon;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean visible ) {
        this.visible = visible;
    }
}
