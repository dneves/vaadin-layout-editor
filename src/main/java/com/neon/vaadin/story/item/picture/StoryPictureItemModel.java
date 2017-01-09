package com.neon.vaadin.story.item.picture;

import com.neon.vaadin.story.item.StoryItemModel;

public class StoryPictureItemModel extends StoryItemModel {

    private String url;

    public StoryPictureItemModel() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
