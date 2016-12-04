package com.neon.storyeditor;

public interface EditableViewInterface<INPUT, OUTPUT> {

    void clear();

    void fill(INPUT input);

    OUTPUT save();
}
