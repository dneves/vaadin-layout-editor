package com.neon.vaadin.layout.editor.component;

import com.neon.layout.ActionsHorizontalLayout;
import com.neon.storyeditor.StoryEditor;
import com.neon.storyeditor.editor.component.IStoryEditorComponent;
import com.neon.storyeditor.editor.component.IStoryEditorComponentCreator;
import com.neon.storyeditor.editor.palette.impl.StoryTextItem;
import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class Block extends VerticalLayout implements LayoutEditorComponent<BlockComponentModel> {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final StoryEditor< String, String > editor = new StoryEditor<>( null );

    private final EditorViewFactory editorViewFactory;

    public Block(EditorViewFactory editorViewFactory) {
        this.editorViewFactory = editorViewFactory;

        addComponent( actions );
        addComponent( editor );

        editor.setMargin( new MarginInfo( true, false, true, false ) );
        editor.setCustomComponentCreator(new IStoryEditorComponentCreator<String, String>() {
            @Override
            public List<IStoryEditorComponent<String, String>> create(Object data) {
                return null;
            }
        });
//        editor.addDesktopDropListener( contentStoryDesktopDropListener );

        editor.addPaletteItems(StoryTextItem::new);
    }

    @Override
    public void addAction(Action action) {
        actions.addAction( action );
    }

    @Override
    public void setModel(BlockComponentModel model) {

    }

    @Override
    public BlockComponentModel getModel() {
        return null;
    }

}
