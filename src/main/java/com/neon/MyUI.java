package com.neon;

import com.neon.vaadin.layout.editor.LayoutEditor;
import com.neon.vaadin.layout.editor.component.Block;
import com.neon.vaadin.layout.editor.component.LayoutEditorComponent;
import com.neon.vaadin.layout.editor.component.model.DynamicBlockModel;
import com.neon.vaadin.layout.editor.component.model.EditorComponentModel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.StringJoiner;

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
        final LayoutEditor editor = new LayoutEditor( 5 );

        VerticalLayout root = new VerticalLayout();

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing( true );
        layout.setMargin( true );
        layout.addComponent( editor );
        layout.setExpandRatio( editor, 1f );

        root.addComponent( layout );
        root.addComponent( new Button( "save", listener -> {
            onSave( editor );
        } ) );
        setContent( root );
    }



    private void onSave( LayoutEditor editor ) {
        StringJoiner j = new StringJoiner("<br>" );

        List<LayoutEditorComponent> model = editor.getModel();
        model.forEach( editorComponent -> {
            EditorComponentModel editorComponentModel = editorComponent.getModel();
            if ( editorComponentModel instanceof DynamicBlockModel ) {
                DynamicBlockModel dynamicBlockModel = ( DynamicBlockModel ) editorComponentModel;

                StringJoiner joiner = new StringJoiner( "," );
                List<Block.BlockModel> blocks = dynamicBlockModel.getBlocks();
                blocks.forEach( block -> {
                    joiner.add( "{ caption=" + block.caption + ", nrColumns=" + block.nrColumns + " }" );
                } );

                j.add( "Row: " + joiner.toString() );
            }
        } );

        Notification notification = new Notification("structure", j.toString(),
                Notification.Type.WARNING_MESSAGE, true );
        notification.show( Page.getCurrent() );

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
