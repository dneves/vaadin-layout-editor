package com.neon.storyeditor.editor.action;

public interface IStoryEditorActionHandler {

    public enum Position {
        LEFT, RIGHT
    }

    public void addAction(final Position position, final StoryComponentAction action);

    public void addAction(final Position position, final StoryComponentAction action, final int index);

}
