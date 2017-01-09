package com.neon.vaadin.upload;

import com.vaadin.server.StreamVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class DesktopFileStreamHandler implements StreamVariable {

    private static final long serialVersionUID = 1L;

    private final File file;

    private final StreamingUploadListener streamingUploadListener;

    private final OutputStream outputStream;

    public DesktopFileStreamHandler( final File file ) throws FileNotFoundException {
        this( file, null );
    }

    public DesktopFileStreamHandler( final File file, final StreamingUploadListener streamingUploadListener ) throws FileNotFoundException {
        this.file = file;
        this.streamingUploadListener = streamingUploadListener;
        this.outputStream = new FileOutputStream( file );
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public boolean listenProgress() {
        return true;
    }

    @Override
    public void onProgress(StreamingProgressEvent event) {
        if (streamingUploadListener != null) {
            streamingUploadListener.onProgress(event);
        }
    }

    @Override
    public void streamingStarted(StreamingStartEvent event) {
        if (streamingUploadListener != null) {
            streamingUploadListener.streamingStarted(event);
        }
    }

    @Override
    public void streamingFinished(StreamingEndEvent event) {
        if (streamingUploadListener != null) {
            streamingUploadListener.streamingFinished(event, file);
        }
    }

    @Override
    public void streamingFailed(StreamingErrorEvent event) {
        if (streamingUploadListener != null) {
            streamingUploadListener.streamingFailed(event);
        }
    }

    @Override
    public boolean isInterrupted() {
        return false;
    }

}
