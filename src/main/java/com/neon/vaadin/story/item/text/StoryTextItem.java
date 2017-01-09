package com.neon.vaadin.story.item.text;

import com.neon.vaadin.story.item.StoryItem;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

public class StoryTextItem extends VerticalLayout implements StoryItem< StoryTextItemModel, StoryTextItemModel > {

    private final RichTextArea richTextArea = new RichTextArea();

    private StoryTextItemModel model;

    public StoryTextItem() {
        richTextArea.setSizeFull();
        this.addComponent( richTextArea );
    }

    @Override
    public void fill( StoryTextItemModel model ) {
        this.model = model;
        richTextArea.setValue( model == null ? null : model.getText() );
    }

    @Override
    public StoryTextItemModel save() {
        return model;
    }

    @Override
    public StoryTextItemModel getModel() {
        return save();
    }
}
