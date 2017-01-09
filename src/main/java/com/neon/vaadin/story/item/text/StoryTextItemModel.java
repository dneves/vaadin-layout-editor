package com.neon.vaadin.story.item.text;

import com.neon.vaadin.story.item.StoryItemModel;

public class StoryTextItemModel extends StoryItemModel {

    private String text;

    public StoryTextItemModel() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
