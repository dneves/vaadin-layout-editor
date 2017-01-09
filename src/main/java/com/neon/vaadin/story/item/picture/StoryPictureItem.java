package com.neon.vaadin.story.item.picture;

import com.neon.vaadin.story.item.StoryItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class StoryPictureItem extends VerticalLayout implements StoryItem< StoryPictureItemModel, StoryPictureItemModel > {

    private final Label label = new Label( "picture" );

    private StoryPictureItemModel model;

    public StoryPictureItem() {
        this.addComponent( label );
    }

    @Override
    public void fill( StoryPictureItemModel model ) {
        this.model = model;
        if ( model != null ) {
            label.setValue( model.getUrl() );
        }
    }

    @Override
    public StoryPictureItemModel save() {
        return model;
    }

    @Override
    public StoryPictureItemModel getModel() {
        return save();
    }
}
