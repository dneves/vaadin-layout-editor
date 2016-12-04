package com.neon.storyeditor.editor.component;

import com.neon.storyeditor.EditableViewInterface;
import com.neon.storyeditor.editor.action.IStoryEditorActionHandler;
import com.vaadin.ui.Component;

public interface IStoryEditorComponent< INPUT, OUTPUT >
        extends Component, EditableViewInterface< INPUT, OUTPUT >, IStoryEditorActionHandler
{

    public boolean isSelected();

    public boolean isFilling();

    void update();

}
