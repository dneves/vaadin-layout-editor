package com.neon.storyeditor;

import com.neon.storyeditor.editor.action.IStoryEditorActionHandler;
import com.neon.storyeditor.editor.action.StoryComponentAction;
import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.neon.storyeditor.editor.component.StoryComponentWrapper;
import com.neon.storyeditor.editor.palette.StoryItemPalette;
import com.vaadin.event.dd.DropHandler;

public class StoryEditorFactory {

    private StoryEditorFactory() {
    }

    protected static <INPUT,OUTPUT> DraggableComponent<StoryComponentWrapper<INPUT, OUTPUT>> create(
            IStoryEditorComponent< INPUT, OUTPUT > editorComponent,
            DropHandler dropHandler,
            StorySortableLayout<INPUT,OUTPUT> layout,
            StoryItemPalette<INPUT, OUTPUT> itemPaletteSpacer) {
        // create draggable editor component
        StoryComponentWrapper<INPUT, OUTPUT> componentWrapper = new StoryComponentWrapper<>(editorComponent, itemPaletteSpacer);
        editorComponent.addStyleName("story-editor-component-layout");

        final DraggableComponent<StoryComponentWrapper<INPUT, OUTPUT>> draggableComponent = new DraggableComponent<>(componentWrapper);
        draggableComponent.setDropHandler( dropHandler );
        draggableComponent.addStyleName( "story-editor-component" );

        itemPaletteSpacer.setParentDraggableComponent(draggableComponent);

        // add editor actions
        editorComponent.addAction( IStoryEditorActionHandler.Position.RIGHT, new StoryComponentAction( "down", "Mover para baixo", null ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void run() {
                layout.moveDown( draggableComponent );
            }
        } );

        editorComponent.addAction( IStoryEditorActionHandler.Position.RIGHT, new StoryComponentAction( "up", "Mover para cima", null ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void run() {
                layout.moveUp( draggableComponent );
            }
        } );

        editorComponent.addAction( IStoryEditorActionHandler.Position.LEFT, new StoryComponentAction( "x", "Apagar Elemento", null ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void run() {
//                context.confirmAction("Tem a certeza que pretende remover o conte\u00FAdo?",
//                                () -> layout.removeComponent( draggableComponent ));
                layout.removeComponent( draggableComponent );
            }
        }, 0 );

        return draggableComponent;
    }

}
