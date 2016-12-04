package com.neon.storyeditor;

import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.neon.storyeditor.editor.component.IStoryEditorComponentCreator;
import com.neon.storyeditor.editor.palette.StoryEditorPaletteItem;
import com.neon.storyeditor.editor.palette.StoryItemPalette;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;


import java.util.List;
import java.util.function.Supplier;


public class StoryEditor< INPUT, OUTPUT > extends VerticalLayout implements
            EditableViewInterface< List<IStoryEditorComponent< INPUT, OUTPUT >>, List< IStoryEditorComponent< INPUT, OUTPUT > > > {

    private static final long serialVersionUID = 2702547390471016034L;

    private final StorySortableLayout< INPUT, OUTPUT > storySortableLayout;

    private final StoryItemPalette< INPUT, OUTPUT > storyItemPalette;


    public StoryEditor( IStoryEditorComponentCreator<INPUT,OUTPUT> customComponentCreator) {
        this.storySortableLayout = new StorySortableLayout<>(customComponentCreator);
        this.storyItemPalette = new StoryItemPalette<>( storySortableLayout );
        setupLayout();
    }

    public void addDesktopDropListener( StoryDesktopDropListener listener ) {
        this.storySortableLayout.addDesktopDropListener( listener );
    }

    public void setCustomComponentCreator( final IStoryEditorComponentCreator< INPUT, OUTPUT > customComponentCreator ) {
        this.storySortableLayout.setCustomComponentCreator( customComponentCreator );
    }


    private void setupLayout() {
        addStyleName( "story-editor" );
        setSizeFull();
        setSpacing( true );
        setMargin( new MarginInfo( true, false, true, false ) );

        addComponent( storySortableLayout );
        addComponent( storyItemPalette );
    }

    public void addPaletteItems(Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>> itemCreator) {
        storyItemPalette.addItem(itemCreator.get());
        storySortableLayout.addPaletteSpacerItems(itemCreator);
    }

    public void removePaletteItems(List<StoryEditorPaletteItem<INPUT, OUTPUT>> paletteItems) {
        for (StoryEditorPaletteItem<INPUT, OUTPUT> paletteItem : paletteItems) {
            storyItemPalette.removeItem(paletteItem);
        }
        storySortableLayout.removePaletteSpacerItems(paletteItems);
    }

    public void removePaletteItemCreator(Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>> itemCreator) {
        storySortableLayout.removePaletteSpacerItemCreator(itemCreator);
    }

    @Override
    public void clear() {
        storySortableLayout.clear();
    }

    @Override
    public void fill( List< IStoryEditorComponent< INPUT, OUTPUT > > input ) {
        storySortableLayout.fill( input );
    }

    @Override
    public List< IStoryEditorComponent< INPUT, OUTPUT > > save() {
        return storySortableLayout.save();
    }

    public void append( List< IStoryEditorComponent< INPUT, OUTPUT > > inputs, int index ) {
        storySortableLayout.append( inputs, index );
    }
}
