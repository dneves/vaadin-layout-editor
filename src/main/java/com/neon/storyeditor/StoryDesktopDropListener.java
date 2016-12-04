package com.neon.storyeditor;

import com.vaadin.server.StreamVariable;
import com.vaadin.ui.Html5File;

import java.io.File;

public interface StoryDesktopDropListener {

    public static class StoryDesktopFile {
        public Html5File html5File;
        public File uploadedFile;
        public boolean isLast;
        public int index;
    }

    public static class ProgressEvent {
        public String fileName;
        public long fileSize;
        public long bytesReceived;
    }


    void onStart(int files);

    void onProgress(ProgressEvent progressEvent);

    void streamingFinished(StoryDesktopFile storyDesktopFile);

    void onFinish(boolean status);

    void streamingFailed(StreamVariable.StreamingErrorEvent streamingErrorEvent);

}
