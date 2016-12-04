package com.neon.vaadin.layout.editor;

import com.vaadin.server.Resource;

public class Action {
    public String label;
    public Resource icon;
    public Runnable listener;
    public boolean isVisible = true;

    public Action() {

    }

    public Action(String label, Resource icon, Runnable listener) {
        this.label = label;
        this.icon = icon;
        this.listener = listener;
    }

    public Action(String label, Resource icon, Runnable listener, boolean isVisible) {
        this.label = label;
        this.icon = icon;
        this.listener = listener;
        this.isVisible = isVisible;
    }

}
