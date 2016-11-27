package com.neon;

import com.neon.dummy.DummyContentsView;
import com.neon.dummy.LayoutEditorViewFactory;
import com.neon.vaadin.layout.editor.LayoutEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

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
        final DummyContentsView dummyContentsView = new DummyContentsView();

        final LayoutEditor editor = new LayoutEditor( new LayoutEditorViewFactory(), dummyContentsView );

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing( true );
        layout.setMargin( true );
        layout.addComponent( editor );
        layout.addComponent( dummyContentsView );
        layout.setExpandRatio( editor, 0.7f );
        layout.setExpandRatio( dummyContentsView, 0.3f );

        setContent( layout );
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
