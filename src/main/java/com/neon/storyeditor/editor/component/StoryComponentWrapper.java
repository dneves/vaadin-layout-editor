package com.neon.storyeditor.editor.component;

import com.neon.storyeditor.editor.palette.StoryItemPalette;
import com.vaadin.ui.VerticalLayout;

public class StoryComponentWrapper<INPUT, OUTPUT> extends VerticalLayout {


    private static final long serialVersionUID = -4013686647582487028L;

    private IStoryEditorComponent<INPUT, OUTPUT> mainComponent;
    private StoryItemPalette<INPUT, OUTPUT> paletteSpacer;

    public StoryComponentWrapper(IStoryEditorComponent<INPUT, OUTPUT> mainComponent, StoryItemPalette<INPUT, OUTPUT> paletteSpacer) {
        this.mainComponent = mainComponent;
        this.paletteSpacer = paletteSpacer;
        setupLayout();
    }

    private void setupLayout(){
        setStyleName("story-component-wrapper");
        addComponent(paletteSpacer);
        addComponent(mainComponent);
    }

    public IStoryEditorComponent<INPUT, OUTPUT> getMainComponent() {
        return mainComponent;
    }

    public void setMainComponent(IStoryEditorComponent<INPUT, OUTPUT> mainComponent) {
        this.mainComponent = mainComponent;
    }

    public StoryItemPalette<INPUT, OUTPUT> getPaletteSpacer() {
        return paletteSpacer;
    }

    public void setPaletteSpacer(StoryItemPalette<INPUT, OUTPUT> paletteSpacer) {
        this.paletteSpacer = paletteSpacer;
    }
}
