package com.neon.vaadin.upload;

import com.vaadin.server.StreamVariable;

import java.io.File;
import java.io.Serializable;

public interface StreamingUploadListener extends Serializable {

    default void onProgress(StreamVariable.StreamingProgressEvent event) {

    }

    default void streamingStarted(StreamVariable.StreamingStartEvent event) {

    }

    default void streamingFinished(StreamVariable.StreamingEndEvent event, File file) {

    }

    default void streamingFailed(StreamVariable.StreamingErrorEvent event) {

    }

}
