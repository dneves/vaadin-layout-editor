package com.neon.vaadin.story;

import com.neon.vaadin.layout.OrderableVerticalLayout;
import com.neon.vaadin.story.item.picture.StoryPictureItem;
import com.neon.vaadin.story.item.text.StoryTextItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class StoryItemOptions extends HorizontalLayout {

    private final Button option = new Button( "+" );

    private final Button text = new Button( "text" );

    private final Button picture = new Button( "picture" );

    private final HorizontalLayout moreOptionsContainer = new HorizontalLayout();


    private final OrderableVerticalLayout targetContainer;

    public StoryItemOptions( OrderableVerticalLayout targetContainer ) {
        this.targetContainer = targetContainer;

        this.addStyleName( "story-item" );

        moreOptionsContainer.setSpacing( true );
        moreOptionsContainer.setMargin( false );
        moreOptionsContainer.addStyleName( "story-options-container" );
        moreOptionsContainer.setVisible( false );

        moreOptionsContainer.addComponent( text );
        moreOptionsContainer.addComponent( picture );

        this.setSpacing( true );
        this.setMargin( false );
        this.addComponent( option );
        this.addComponent(moreOptionsContainer);

        option.addClickListener((Button.ClickListener) clickEvent -> {
            toggleMoreOptionsContainer();
        } );

        text.addClickListener((Button.ClickListener) clickEvent -> { newText(); toggleMoreOptionsContainer(); } );
        picture.addClickListener((Button.ClickListener) clickEvent -> { newPicture(); toggleMoreOptionsContainer(); } );
    }

    private void toggleMoreOptionsContainer() {
        moreOptionsContainer.setVisible( ! moreOptionsContainer.isVisible() );
        option.setCaption( moreOptionsContainer.isVisible() ? "x" : "+" );
    }

    private void newText() {
        StoryTextItem item = new StoryTextItem();
        targetContainer.handle( item, targetContainer.getComponentCount() );
    }

    private void newPicture() {
        StoryPictureItem item = new StoryPictureItem();
        targetContainer.handle( item, targetContainer.getComponentCount() );
    }

}
