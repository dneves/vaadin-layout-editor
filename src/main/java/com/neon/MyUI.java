package com.neon;

import com.neon.dnd.Draggable;
import com.neon.dummy.Content;
import com.neon.dummy.DummyContentsView;
import com.neon.dummy.LayoutEditorViewFactory;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.LayoutEditor;
import com.neon.vaadin.layout.editor.component.Block;
import com.neon.vaadin.layout.editor.component.BlockFactory;
import com.neon.vaadin.layout.editor.component.DynamicBlock;
import com.neon.vaadin.layout.editor.component.DynamicFactory;
import com.neon.vaadin.layout.editor.component.LayoutEditorComponent;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.DynamicBlockModel;
import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.neon.vaadin.story.Story;
import com.neon.vaadin.upload.CacheProvider;
import com.neon.vaadin.upload.DesktopDropHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {

        CacheProvider cacheProvider = new CacheProvider() {
            @Override
            public File createTempFile(String filename) throws IOException {
                String prefix = UUID.randomUUID().toString();
                String suffix = filename;

                Path tempFilePath = Files.createTempFile(prefix, suffix);
                return tempFilePath.toFile();
            }
        };

        DesktopDropHandler desktopDropHandler = new DesktopDropHandler( cacheProvider );

        Story story = new Story( desktopDropHandler );


//        desktopDropHandler.addDesktopDropListener(new DesktopDropHandler.DesktopDropListener() {
//            @Override
//            public void streamingFinished( StoryDesktopFile file ) {
//                story.append( , file.index );
//            }
//        });



        HorizontalLayout layout = new HorizontalLayout( story );
        layout.setSizeFull();

        story.setWidth( 50, Unit.PERCENTAGE );
        layout.setComponentAlignment( story, Alignment.TOP_CENTER );

        setContent( layout );
    }

//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//        final EditorViewFactory editorViewFactory = new LayoutEditorViewFactory();
//
//        final DummyContentsView dummyContentsView = new DummyContentsView();
//
//        final LayoutEditor editor = new LayoutEditor( editorViewFactory );
//
//        VerticalLayout root = new VerticalLayout();
//
//        final HorizontalLayout layout = new HorizontalLayout();
//        layout.setSizeFull();
//        layout.setSpacing( true );
//        layout.setMargin( true );
//        layout.addComponent( editor );
//        layout.addComponent( dummyContentsView );
//        layout.setExpandRatio( editor, 0.7f );
//        layout.setExpandRatio( dummyContentsView, 0.3f );
//
//        root.addComponent( layout );
//        root.addComponent( new Button( "save", listener -> {
//            onSave( editor );
//        } ) );
//        setContent( root );
//
//
//        setModel( editor, editorViewFactory );
//    }
//
//
//
//    private void onSave( LayoutEditor editor ) {
//        List<LayoutEditorComponent> model = editor.getModel();
//
//        StringBuilder sb = new StringBuilder();
//
//        model.forEach( component -> {
//            EditorComponentModel editorComponentModel = component.getModel();
//            if ( editorComponentModel instanceof BlockComponentModel ) {
//                List<Draggable> contents = ((BlockComponentModel) editorComponentModel).getContents();
//                sb.append(component).append(" => ").append(contents.size());
//                sb.append( "<br>" );
//                contents.forEach( d -> {
//                    sb.append( d.getModel().getId() );
//                    sb.append( " " );
//                } );
//                sb.append( "<br><br>" );
//            }
//        } );
//
//        Notification notification = new Notification("", sb.toString(), Notification.Type.WARNING_MESSAGE, true);
//        notification.show(Page.getCurrent() );
//    }
//
//    private void setModel( LayoutEditor editor, EditorViewFactory editorViewFactory ) {
//        List<LayoutEditorComponent > model = new LinkedList<>();
//
//        model.add( createDynamicBlock( editorViewFactory ) );
//        model.add( createBlock( editorViewFactory ) );
//
//        editor.setModel( model );
//    }
//
//    private LayoutEditorComponent createDynamicBlock( EditorViewFactory editorViewFactory ) {
//        DynamicBlock dynamicBlock = DynamicFactory.create(editorViewFactory);
//
//        List<Draggable> draggables = new LinkedList<>();
//        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
//        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
//
//        BlockComponentModel blockComponentModel = new BlockComponentModel();
//        blockComponentModel.setContents( draggables );
//
//        DynamicBlockModel model = new DynamicBlockModel();
//        model.setBlocks(Arrays.asList( blockComponentModel ) );
//
//        dynamicBlock.setModel( model );
//
//        return dynamicBlock;
//    }
//
//    private LayoutEditorComponent createBlock( EditorViewFactory editorViewFactory ) {
//        Block block = BlockFactory.create( editorViewFactory );
//
//        List<Draggable> draggables = new LinkedList<>();
//        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
//        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
//
//        BlockComponentModel blockComponentModel = new BlockComponentModel();
//        blockComponentModel.setContents( draggables );
//
//        block.setModel( blockComponentModel );
//        return block;
//    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
