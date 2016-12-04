package com.neon.storyeditor;

import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.neon.storyeditor.editor.component.IStoryEditorComponentCreator;
import com.neon.storyeditor.editor.component.StoryComponentWrapper;
import com.neon.storyeditor.editor.palette.StoryEditorPaletteItem;
import com.neon.storyeditor.editor.palette.StoryItemPalette;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;


public class StorySortableLayout< INPUT, OUTPUT >
        extends VerticalLayout
        implements EditableViewInterface< List<IStoryEditorComponent< INPUT, OUTPUT >>, List< IStoryEditorComponent< INPUT, OUTPUT > > > {

    private static final long serialVersionUID = 6172417255463225269L;

    private final DroppableComponent< Label > spacer = new DroppableComponent<>( new Label( "Arraste para aqui" ) );

    private final StoryReorderDropHandler< INPUT, OUTPUT > storyReorderDropHandler = new StoryReorderDropHandler<>( this );

    private IStoryEditorComponentCreator< INPUT, OUTPUT > customComponentCreator;

    private final List<StoryItemPalette<INPUT, OUTPUT>> paletteSpacers = new LinkedList<>();

    private final List<Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>>> paletteSpacerItemCreators = new LinkedList<>();


    public StorySortableLayout(IStoryEditorComponentCreator< INPUT, OUTPUT > customComponentCreator ) {
        this.customComponentCreator = customComponentCreator;

        spacer.addStyleName("story-spacer");
        spacer.setSizeUndefined();
        spacer.setDropHandler( storyReorderDropHandler );

        this.addStyleName( "story-sortable-layout" );
        this.addComponent( spacer );
    }

    public void addDesktopDropListener( StoryDesktopDropListener listener ) {
        storyReorderDropHandler.addDesktopDropListener( listener );
    }

    public void setCustomComponentCreator( IStoryEditorComponentCreator< INPUT, OUTPUT > customComponentCreator ) {
        this.customComponentCreator = customComponentCreator;
    }


    public void handle( final Object something ) {
        handle( something, getComponentCount() );
    }

    public void handle( final Object something, final int index ) {
        if ( something instanceof AbstractComponent ) {
            AbstractComponent abstractComponent = ( AbstractComponent ) something;
            handleData( abstractComponent.getData(), index );
        } else {
            handleData( something, index );
        }
    }

    private void handleData( final Object data, final int index ) {
        if ( data instanceof StoryEditorPaletteItem ) {
            final StoryEditorPaletteItem< INPUT, OUTPUT > paletteItem = ( StoryEditorPaletteItem< INPUT, OUTPUT > ) data;

            paletteItem.createEditorComponent( editorComponent -> addEditorComponent( editorComponent, index ) );

        } else if ( data instanceof IStoryEditorComponent ) {
            final IStoryEditorComponent< INPUT, OUTPUT > editorComponent = ( IStoryEditorComponent< INPUT, OUTPUT > ) data;

            addEditorComponent( editorComponent, index );

        } else if ( data != null ) {

            if ( customComponentCreator != null ) {
                List< IStoryEditorComponent< INPUT, OUTPUT > > editorComponents = customComponentCreator.create( data );
                if ( editorComponents != null ) {
                    int idx = index;
                    for ( IStoryEditorComponent< INPUT, OUTPUT > editorComponent : editorComponents ) {
                        addEditorComponent( editorComponent, idx++ );
                    }
                }
            }

        }
    }


    private DraggableComponent<StoryComponentWrapper<INPUT, OUTPUT>> addEditorComponent(final IStoryEditorComponent< INPUT, OUTPUT > editorComponent, int index ) {
        if ( editorComponent == null || editorComponent.getParent() != null ) {
            return null;
        }

        StoryItemPalette<INPUT, OUTPUT> paletteSpacer = createPaletteSpacer();
        paletteSpacers.add(paletteSpacer);

        DraggableComponent<StoryComponentWrapper<INPUT, OUTPUT>> component =
                StoryEditorFactory.create(editorComponent, storyReorderDropHandler, this, paletteSpacer);

        if ( spacer.isAttached() ) {
            index = 0;
        }
        super.removeComponent( spacer );
        this.addComponent( component, index );

        fireChangeEvent();
        return component;
    }

    @Override
    public void removeComponent( Component c ) {
        StoryComponentWrapper<INPUT, OUTPUT> wrapper = toStoryComponentWrapper(c);
        if(wrapper != null) {
            paletteSpacers.remove(wrapper.getPaletteSpacer());
        }

        super.removeComponent( c );
        if ( getComponentCount() == 0 ) {
            super.addComponent( spacer );
        }
        fireChangeEvent();
    }

    private void fireChangeEvent() {

    }


    private boolean isFilling = false;

    @Override
    public void clear() {
        this.removeAllComponents();
    }

    @Override
    public void fill( List< IStoryEditorComponent< INPUT, OUTPUT > > input ) {
        this.isFilling = true;

        clear();

        append( input, 0 );

        this.isFilling = false;
    }

    @Override
    public List< IStoryEditorComponent< INPUT, OUTPUT > > save() {
        List< IStoryEditorComponent< INPUT, OUTPUT > > result = new LinkedList<>(  );

        for ( Component component : this ) {
            StoryComponentWrapper<INPUT, OUTPUT> storyComponentWrapper = toStoryComponentWrapper(component);
            if(storyComponentWrapper != null) {
                result.add( storyComponentWrapper.getMainComponent() );
            }
        }

        return result;
    }

    public void append( List< IStoryEditorComponent< INPUT, OUTPUT > > input, int index ) {
        if ( input == null ) {
            return ;
        }

        for ( IStoryEditorComponent< INPUT, OUTPUT > editorComponent : input ) {
            addEditorComponent( editorComponent, index++ );
        }
    }

    public void moveUp( final Component c ) {
        int currentIndex = getComponentIndex( c );
        if ( currentIndex > 0 ) {
            removeComponent( c );
            addComponent( c, currentIndex - 1 );

            fireChangeEvent();
        }
    }

    public void moveDown( final Component c ) {
        int currentIndex = getComponentIndex( c );
        int componentCount = getComponentCount();
        if ( currentIndex < componentCount - 1 ) {
            removeComponent( c );
            addComponent( c, currentIndex + 1 );

            fireChangeEvent();
        }
    }

    public void addPaletteSpacerItems(Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>> itemCreator) {
        paletteSpacerItemCreators.add(itemCreator);
        for (StoryItemPalette<INPUT, OUTPUT> paletteSpacer : paletteSpacers) {
            paletteSpacer.addItem(itemCreator.get());
        }
    }

    public void removePaletteSpacerItems(List<StoryEditorPaletteItem<INPUT, OUTPUT>> paletteItems) {
        for (StoryEditorPaletteItem<INPUT, OUTPUT> paletteItem : paletteItems) {
            for (StoryItemPalette<INPUT, OUTPUT> paletteSpacer : paletteSpacers) {
                paletteSpacer.removeItem(paletteItem);
            }
        }
    }

    public void removePaletteSpacerItemCreator(Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>> itemCreator) {
        paletteSpacerItemCreators.remove(itemCreator);
    }

    private StoryItemPalette<INPUT, OUTPUT> createPaletteSpacer(){
        StoryItemPalette<INPUT, OUTPUT> result = new StoryItemPalette<>(this);
        for (Supplier<StoryEditorPaletteItem<INPUT, OUTPUT>> paletteSpacerItemCreator : paletteSpacerItemCreators) {
            result.addItem(paletteSpacerItemCreator.get());
        }
        return result;
    }

    private StoryComponentWrapper<INPUT, OUTPUT> toStoryComponentWrapper(Component component) {
        if (!(component instanceof DraggableComponent)) {
            return null;
        }

        DraggableComponent<StoryComponentWrapper<INPUT, OUTPUT>> elementWrapper =
            (DraggableComponent< StoryComponentWrapper<INPUT, OUTPUT>>) component;

        return elementWrapper.getMainComponent();
    }
}
