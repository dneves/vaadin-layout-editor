package com.neon.storyeditor.editor.palette.impl;

import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.neon.storyeditor.editor.component.impl.StoryTextComponent;
import com.neon.storyeditor.editor.palette.StoryEditorPaletteItem;

import java.util.function.Consumer;

public class StoryTextItem extends StoryEditorPaletteItem< String, String > {

    public StoryTextItem() {
        super(  "Texto", null );
    }

    @Override
    public void createEditorComponent( Consumer<IStoryEditorComponent< String, String >> consume ) {
        StoryTextComponent textComponent = new StoryTextComponent( true );
        consume.accept( textComponent );
    }

}
