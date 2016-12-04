package com.neon.storyeditor.editor.palette;

import com.neon.storyeditor.DraggableComponent;
import com.neon.storyeditor.StorySortableLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import java.util.HashMap;
import java.util.Map;

public class StoryItemPalette< INPUT, OUTPUT > extends HorizontalLayout {

    private static final long serialVersionUID = 4796189122864256773L;

    private final StorySortableLayout< INPUT, OUTPUT > paper;

    private DraggableComponent<?> parentDraggableComponent;

    private final Map<StoryEditorPaletteItem<INPUT, OUTPUT>, DraggableComponent<Button>> paletteItemWrappersMap = new HashMap<>();

    public StoryItemPalette( final StorySortableLayout< INPUT, OUTPUT > paper ) {
        this.paper = paper;

        setSpacing( true );
        setSizeUndefined();
        addStyleName("story-item-palette");
    }

    public void addItem( StoryEditorPaletteItem< INPUT, OUTPUT > item ) {
        if ( item == null ) {
            return ;
        }

        final Button button = new Button( item.getLabel() );
        button.addStyleName( "palette-item" );
        button.setIcon( item.getIcon() );
        button.setVisible(item.isVisible());

        final DraggableComponent< Button > wrapper = new DraggableComponent<>( button );
        wrapper.addStyleName( "palette-item-wrapper" );
        wrapper.setSizeUndefined();
        wrapper.setData( item );

        button.addClickListener( event -> {
            if (parentDraggableComponent != null){
                int index = paper.getComponentIndex(parentDraggableComponent);
                paper.handle(wrapper, index);
            }else{
                paper.handle( wrapper);
            }
        } );

        paletteItemWrappersMap.put(item, wrapper);
        this.addComponent( wrapper );
    }

    public void removeItem(StoryEditorPaletteItem< INPUT, OUTPUT > item) {
        DraggableComponent<Button> component = paletteItemWrappersMap.get(item);
        if(component != null) {
            removeComponent(component);
            paletteItemWrappersMap.remove(item);
        }
    }

    public DraggableComponent<?> getParentDraggableComponent() {
        return parentDraggableComponent;
    }

    public void setParentDraggableComponent(DraggableComponent<?> parentDraggableComponent) {
        this.parentDraggableComponent = parentDraggableComponent;
    }
}
