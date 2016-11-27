package com.neon;

import com.neon.dnd.Draggable;
import com.neon.dummy.Content;
import com.neon.dummy.DummyContentsView;
import com.neon.dummy.LayoutEditorViewFactory;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.LayoutEditor;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.neon.vaadin.layout.editor.component.Block;
import com.neon.vaadin.layout.editor.component.BlockFactory;
import com.neon.vaadin.layout.editor.component.Columns;
import com.neon.vaadin.layout.editor.component.ColumnsFactory;
import com.neon.vaadin.layout.editor.component.LayoutEditorComponent;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;
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

    final boolean removeFromExternalSource = false;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final EditorViewFactory editorViewFactory = new LayoutEditorViewFactory();

        final DummyContentsView dummyContentsView = new DummyContentsView();

        final LayoutEditor editor = new LayoutEditor( editorViewFactory, dummyContentsView );
        editor.setRemoveFromExternalSource( removeFromExternalSource );

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing( true );
        layout.setMargin( true );
        layout.addComponent( editor );
        layout.addComponent( dummyContentsView );
        layout.setExpandRatio( editor, 0.7f );
        layout.setExpandRatio( dummyContentsView, 0.3f );

        setContent( layout );


        List<LayoutEditorComponent > model = new LinkedList<>();

        model.add( createBlock( editorViewFactory, dummyContentsView ) );
        model.add( createColumns( editorViewFactory, dummyContentsView ) );
        model.add( createBlock( editorViewFactory, dummyContentsView ) );

        editor.setModel( model );
    }

    private LayoutEditorComponent createBlock(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        Block block = BlockFactory.create(editorViewFactory, sourceComponentsHolder, removeFromExternalSource );

        List<List<Draggable>> model = new LinkedList<>();
        List<Draggable> draggables = new LinkedList<>();
        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        model.add( draggables );
        block.setModel( model );

        return block;
    }

    private LayoutEditorComponent createColumns(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        Columns columns = ColumnsFactory.create( editorViewFactory, sourceComponentsHolder, removeFromExternalSource );

        List<List<Draggable>> model = new LinkedList<>();

        List<Draggable> left = new LinkedList<>();
        left.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        left.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        model.add( left );

        List<Draggable> right = new LinkedList<>();
        right.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        right.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        model.add( right );

        columns.setModel( model );

        return columns;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
