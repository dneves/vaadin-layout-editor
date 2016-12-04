package com.neon.storyeditor;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamVariable;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Html5File;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class handles all dropped 'component's into the editor view.
 */
public class StoryReorderDropHandler< INPUT, OUTPUT > implements DropHandler {

    private static final long serialVersionUID = 2134776737049515091L;

    private static final String VERTICAL_LOCATION = "verticalLocation";

    private StorySortableLayout< INPUT, OUTPUT > targetLayout;

    private final List< StoryDesktopDropListener > listeners = Collections.synchronizedList( new LinkedList<>(  ) );

    public StoryReorderDropHandler( final StorySortableLayout< INPUT, OUTPUT > storySortableLayout ) {
        this.targetLayout = storySortableLayout;
    }

    public void addDesktopDropListener( StoryDesktopDropListener desktopDropListener ) {
        if ( desktopDropListener != null ) {
            listeners.add( desktopDropListener );
        }
    }

    @Override
    public void drop( DragAndDropEvent event ) {
        Transferable transferable = event.getTransferable();
        Component sourceComponent = transferable.getSourceComponent();
        TargetDetails dropTargetData = event.getTargetDetails();

        Component parentSortableLayout = sourceComponent.getParent();

        boolean isDesktopDrop = ( transferable instanceof DragAndDropWrapper.WrapperTransferable
                && ( ( ( DragAndDropWrapper.WrapperTransferable ) transferable ).getFiles() != null ) );

        int index;
        // desktop dropped shit does not compute sourceComponent
        if ( isDesktopDrop ) {
            index = getDesktopDropIndex( dropTargetData.getTarget(), dropTargetData );
        } else {
            index = getDropIndex( sourceComponent, dropTargetData.getTarget(), dropTargetData );
        }

        if ( isDesktopDrop ) {
            // transferable is a WrapperTransferable and has files dragged from outside browser
            handleDesktopDrop( ( DragAndDropWrapper.WrapperTransferable ) transferable, index );
        } else {
            if ( parentSortableLayout != targetLayout ) { // items from outside layout
                targetLayout.handle( sourceComponent, index );
            } else { // reorder items inside layout
                targetLayout.removeComponent( sourceComponent );
                targetLayout.addComponent( sourceComponent, index );
            }
        }
    }

    private int getDesktopDropIndex( final Component targetComponent, TargetDetails targetDetails ) {
        int index = 0;
        Iterator<Component> iterator = targetLayout.iterator();
        Component next = null;
        while (next != targetComponent && iterator.hasNext()) {
            next = iterator.next();
            index++;
        }

        if ( targetDetails.getData( VERTICAL_LOCATION ).equals( VerticalDropLocation.MIDDLE.toString() ) ) {
            index--;
        }
        if ( targetDetails.getData( VERTICAL_LOCATION ).equals( VerticalDropLocation.TOP.toString() ) ) {
            index--;
        }

        return index < 0 ? 0 : index;
    }

    private int getDropIndex( Component sourceComponent, Component targetComponent, TargetDetails targetDetails ) {
        int index = 0;
        boolean sourceWasAfterTarget = true;

        Iterator<Component> iterator = targetLayout.iterator();
        Component next = null;
        while (next != targetComponent && iterator.hasNext()) {
            next = iterator.next();
            if (next != sourceComponent) {
                index++;
            } else {
                sourceWasAfterTarget = false;
            }
        }

        if (targetDetails.getData(VERTICAL_LOCATION).equals(VerticalDropLocation.MIDDLE.toString())) {
            if (sourceWasAfterTarget) {
                index--;
            }
        }

        if (targetDetails.getData(VERTICAL_LOCATION).equals(VerticalDropLocation.TOP.toString())) {
            index--;
            if ( index <= 0 ) {
                index = 0;
            }
        }

        return index;
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }



    private void handleDesktopDrop( final DragAndDropWrapper.WrapperTransferable wrapperTransferable, final int index ) {
//        Html5File[] html5Files = wrapperTransferable.getFiles();
//        if ( html5Files == null || html5Files.length == 0 ) {
//            return ;
//        }
//
//        fireStartEvent( html5Files.length );
//
//        int i = 1;
//        for ( final Html5File html5File : html5Files ) {
//            final boolean isLastUploadedFile = i == html5Files.length;
//
//            // only allow images/videos
//            if ( ! html5File.getType().startsWith( "image" ) && ! html5File.getType().startsWith( "video" )
//                    && ! WebContentExtractor.isSupportedMimeType( html5File.getType() ) ) {
//                if ( isLastUploadedFile ) {
//                    fireFinishEvent( true );
//                }
//                continue;
//            }
//
//            try {
//                String tempFilePath = ContentImageUtils.putImageInTempCache( html5File.getFileName(), new ByteArrayInputStream( new byte[] { } ) );
//                final File tempFile = new File( tempFilePath );
//                final int idx = i - 1;
//
//                html5File.setStreamVariable( new DesktopFileStreamHandler( tempFile, new StreamingUploadAdapter() {
//                    @Override
//                    public void onProgress( StreamVariable.StreamingProgressEvent event ) {
//                        StoryDesktopDropListener.ProgressEvent progressEvent = new StoryDesktopDropListener.ProgressEvent();
//                        progressEvent.fileName = html5File.getFileName();
//                        progressEvent.bytesReceived = event.getBytesReceived();
//                        progressEvent.fileSize = html5File.getFileSize();
//
//                        fireProgressEvent( progressEvent );
//                    }
//
//                    @Override
//                    public void streamingFinished( StreamVariable.StreamingEndEvent event, File file ) {
//                        StoryDesktopDropListener.StoryDesktopFile storyDesktopFile = new StoryDesktopDropListener.StoryDesktopFile();
//                        storyDesktopFile.html5File = html5File;
//                        storyDesktopFile.uploadedFile = file;
//                        storyDesktopFile.isLast = isLastUploadedFile;
//                        storyDesktopFile.index = index + idx;
//
//                        fireStreamingFinished( storyDesktopFile );
//                        if ( isLastUploadedFile ) {
//                            fireFinishEvent( true );
//                        }
//                    }
//
//                    @Override
//                    public void streamingFailed( StreamVariable.StreamingErrorEvent event ) {
//                        fireStreamingFailed( event );
//                        if ( isLastUploadedFile ) {
//                            fireFinishEvent( false );
//                        }
//                    }
//                } ) );
//            } catch ( IOException e ) {
//                ILog.get( StoryReorderDropHandler.class ).error( e.getMessage(), e );
//            }
//            i++;
//        }
    }


    private void fireStartEvent( final int count ) {
        listeners.forEach( listener -> listener.onStart( count ) );
    }
    private void fireFinishEvent( boolean status ) {
        listeners.forEach( listener -> listener.onFinish( status ) );
    }
    private void fireProgressEvent( StoryDesktopDropListener.ProgressEvent event ) {
        listeners.forEach( listener -> listener.onProgress( event ) );
    }
    private void fireStreamingFinished( StoryDesktopDropListener.StoryDesktopFile storyDesktopFile ) {
        listeners.forEach( listener -> listener.streamingFinished( storyDesktopFile ) );
    }
    private void fireStreamingFailed( StreamVariable.StreamingErrorEvent event ) {
        listeners.forEach( listener -> listener.streamingFailed( event ) );
    }

}
