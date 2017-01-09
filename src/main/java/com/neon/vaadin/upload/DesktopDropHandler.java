package com.neon.vaadin.upload;

import com.neon.vaadin.layout.OrderableVerticalLayoutDropHandler;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Html5File;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DesktopDropHandler {

    public interface DesktopDropListener {

        class StoryDesktopFile {
            public Html5File html5File;
            public File uploadedFile;
            public boolean isLast;
            public int index;
        }

        class ProgressEvent {
            public String fileName;
            public long fileSize;
            public long bytesReceived;
        }


        default void onStart(int files) {

        }

        default void onProgress(DesktopDropListener.ProgressEvent progressEvent) {

        }

        default void streamingFinished(DesktopDropListener.StoryDesktopFile storyDesktopFile) {

        }

        default void onFinish(boolean status) {

        }

        default void streamingFailed(StreamVariable.StreamingErrorEvent streamingErrorEvent) {

        }

    }

    private final List< DesktopDropListener > listeners = Collections.synchronizedList( new LinkedList<>(  ) );

    private final CacheProvider cacheProvider;


    public DesktopDropHandler( CacheProvider cacheProvider ) {
        this.cacheProvider = cacheProvider;
    }


    public void addDesktopDropListener( DesktopDropListener desktopDropListener ) {
        if ( desktopDropListener != null ) {
            listeners.add( desktopDropListener );
        }
    }
    public void removeDesktopDropListener( DesktopDropListener listener ) {
        if ( listener != null ) {
            listeners.remove( listener );
        }
    }

    public void handleDesktopDrop( final DragAndDropWrapper.WrapperTransferable wrapperTransferable, final int index ) {
        Html5File[] html5Files = wrapperTransferable.getFiles();
        if ( html5Files == null || html5Files.length == 0 ) {
            return ;
        }

        fireStartEvent( html5Files.length );

        int i = 1;
        for ( final Html5File html5File : html5Files ) {
            final boolean isLastUploadedFile = i == html5Files.length;

            // only allow images/videos
            if ( ! html5File.getType().startsWith( "image" ) && ! html5File.getType().startsWith( "video" )
//                    && ! WebContentExtractor.isSupportedMimeType( html5File.getType() )
            ) {
                if ( isLastUploadedFile ) {
                    fireFinishEvent( true );
                }
                continue;
            }

            try {
                final File tempFile = cacheProvider.createTempFile(html5File.getFileName());
                final int idx = i - 1;
                html5File.setStreamVariable( new DesktopFileStreamHandler( tempFile, new StreamingUploadListener() {
                    @Override
                    public void onProgress( StreamVariable.StreamingProgressEvent event ) {
                        DesktopDropListener.ProgressEvent progressEvent = new DesktopDropListener.ProgressEvent();
                        progressEvent.fileName = html5File.getFileName();
                        progressEvent.bytesReceived = event.getBytesReceived();
                        progressEvent.fileSize = html5File.getFileSize();

                        fireProgressEvent( progressEvent );
                    }

                    @Override
                    public void streamingFinished( StreamVariable.StreamingEndEvent event, File file ) {
                        DesktopDropListener.StoryDesktopFile storyDesktopFile = new DesktopDropListener.StoryDesktopFile();
                        storyDesktopFile.html5File = html5File;
                        storyDesktopFile.uploadedFile = file;
                        storyDesktopFile.isLast = isLastUploadedFile;
                        storyDesktopFile.index = index + idx;

                        fireStreamingFinished( storyDesktopFile );
                        if ( isLastUploadedFile ) {
                            fireFinishEvent( true );
                        }
                    }

                    @Override
                    public void streamingFailed( StreamVariable.StreamingErrorEvent event ) {
                        fireStreamingFailed( event );
                        if ( isLastUploadedFile ) {
                            fireFinishEvent( false );
                        }
                    }
                } ) );
            } catch ( Exception e ) {
                LoggerFactory.getLogger( OrderableVerticalLayoutDropHandler.class ).error( e.getLocalizedMessage(), e );
            }
            i++;
        }
    }

    private void fireStartEvent( final int count ) {
        listeners.forEach( listener -> listener.onStart( count ) );
    }
    private void fireFinishEvent( boolean status ) {
        listeners.forEach( listener -> listener.onFinish( status ) );
    }
    private void fireProgressEvent( DesktopDropListener.ProgressEvent event ) {
        listeners.forEach( listener -> listener.onProgress( event ) );
    }
    private void fireStreamingFinished( DesktopDropListener.StoryDesktopFile storyDesktopFile ) {
        listeners.forEach( listener -> listener.streamingFinished( storyDesktopFile ) );
    }
    private void fireStreamingFailed( StreamVariable.StreamingErrorEvent event ) {
        listeners.forEach( listener -> listener.streamingFailed( event ) );
    }

}
