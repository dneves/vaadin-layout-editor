package com.neon.storyeditor.editor.component;

import java.util.List;

public interface IStoryEditorComponentCreator< INPUT, OUTPUT > {

    public List< IStoryEditorComponent< INPUT, OUTPUT > > create(Object data);

}
