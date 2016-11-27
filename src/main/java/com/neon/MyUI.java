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
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.ColumnsComponentModel;
import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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

        VerticalLayout root = new VerticalLayout();

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing( true );
        layout.setMargin( true );
        layout.addComponent( editor );
        layout.addComponent( dummyContentsView );
        layout.setExpandRatio( editor, 0.7f );
        layout.setExpandRatio( dummyContentsView, 0.3f );

        root.addComponent( layout );
        root.addComponent( new Button( "save", listener -> {
            List<LayoutEditorComponent> model = editor.getModel();
            model.forEach( component -> {
                EditorComponentModel editorComponentModel = component.getModel();
                if ( editorComponentModel instanceof BlockComponentModel ) {
                    List<Draggable> contents = ((BlockComponentModel) editorComponentModel).getContents();
                    System.out.println( component + " => " + contents.size() );
                    contents.forEach( d -> {
                        System.out.println( "\t" + d.getModel().getId() );
                    } );
                } else if ( editorComponentModel instanceof ColumnsComponentModel ) {
                    Object sizes = ((ColumnsComponentModel) editorComponentModel).getSizes();
                    List<Draggable> contents1 = ((ColumnsComponentModel) editorComponentModel).getColumn1().getContents();
                    List<Draggable> contents2 = ((ColumnsComponentModel) editorComponentModel).getColumn2().getContents();
                    System.out.println( component + " => " + sizes + " [ " + contents1.size() + ", " + contents2.size() + " ]" );

                    System.out.print( "\t[ " );
                    contents1.forEach( d -> {
                        System.out.print( d.getModel().getId() + " " );
                    } );
                    System.out.println( "]" );
                    System.out.print( "\t[ " );
                    contents2.forEach( d -> {
                        System.out.print( d.getModel().getId() + " " );
                    } );
                    System.out.println( "]" );
                }
            } );
        } ) );
        setContent( root );


        setModel( editor, editorViewFactory, dummyContentsView );
    }


    private void setModel( LayoutEditor editor, EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        List<LayoutEditorComponent > model = new LinkedList<>();

        model.add( createBlock( editorViewFactory, sourceComponentsHolder ) );
        model.add( createColumns( editorViewFactory, sourceComponentsHolder ) );
        model.add( createBlock( editorViewFactory, sourceComponentsHolder ) );

        editor.setModel( model );
    }

    private LayoutEditorComponent createBlock(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        Block block = BlockFactory.create(editorViewFactory, sourceComponentsHolder, removeFromExternalSource );

        List<Draggable> draggables = new LinkedList<>();
        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        draggables.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );

        BlockComponentModel blockComponentModel = new BlockComponentModel();
        blockComponentModel.setContents( draggables );

        block.setModel( blockComponentModel );
        return block;
    }

    private LayoutEditorComponent createColumns(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder) {
        Columns columns = ColumnsFactory.create( editorViewFactory, sourceComponentsHolder, removeFromExternalSource );

        List<Draggable> left = new LinkedList<>();
        left.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        left.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );

        BlockComponentModel blockComponentModel1 = new BlockComponentModel();
        blockComponentModel1.setContents( left );

        List<Draggable> right = new LinkedList<>();
        right.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );
        right.add( editorViewFactory.create( new Content(UUID.randomUUID().toString()) ) );

        BlockComponentModel blockComponentModel2 = new BlockComponentModel();
        blockComponentModel2.setContents( right );

        ColumnsComponentModel columnsComponentModel = new ColumnsComponentModel();
        columnsComponentModel.setSizes( "left" );
        columnsComponentModel.setColumn1( blockComponentModel1 );
        columnsComponentModel.setColumn2( blockComponentModel2 );

        columns.setModel( columnsComponentModel );

        return columns;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
