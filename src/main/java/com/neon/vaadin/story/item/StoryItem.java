package com.neon.vaadin.story.item;

import com.neon.vaadin.dnd.Draggable;

public interface StoryItem< INPUT, OUTPUT > extends Draggable< OUTPUT > {

    void fill( INPUT input );

    OUTPUT save();
}
