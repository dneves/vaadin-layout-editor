package com.neon.storyeditor.editor.component;

import com.neon.storyeditor.EditableViewInterface;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;


public class InlineTextEditor extends CustomComponent
        implements EditableViewInterface< String, String >, Property.ValueChangeNotifier {

    private static final long serialVersionUID = 4739118880044501480L;

    private final Property< String > property = new ObjectProperty<>( "" );

//    private final CKEditorTextField textArea = new CKEditorTextField();
    private final TextArea textArea = new TextArea();

    private final Component editableComponent;

    private final Component readOnlyComponent;


    public InlineTextEditor() {
        editableComponent = setupEditable();
        readOnlyComponent = setupReadOnly();

        this.setWidth( 100, Unit.PERCENTAGE );
        this.setCompositionRoot( editableComponent );
    }

    private Component setupReadOnly() {
        final Label label = new Label( property, ContentMode.HTML );

        final CssLayout result = new CssLayout();
        result.addComponent( label );
        result.addStyleName("story-text-readonly-mode");
        result.setSizeFull();

        return result;
    }

    private Component setupEditable() {
        textArea.setPropertyDataSource( property );
        textArea.setSizeFull();
        textArea.addAttachListener(new AttachListener() {
            private static final long serialVersionUID = 7544437641863088059L;

            @Override
            public void attach(AttachEvent event) {
                textArea.focus();
            }
        });

        CssLayout result = new CssLayout();
        result.addComponent( textArea );
        result.addStyleName( "story-text-edit-mode" );
        result.setSizeFull();

        return result;
    }

    @Override
    public void clear() {
        property.setValue( "" );
    }

    @Override
    public void fill( String value ) {
        property.setValue( value );
    }

    @Override
    public String save() {
        return property.getValue();
    }

    @Override
    public void addValueChangeListener( Property.ValueChangeListener valueChangeListener ) {
        textArea.addValueChangeListener( valueChangeListener );
    }

    @Override
    public void removeValueChangeListener( Property.ValueChangeListener valueChangeListener ) {
        textArea.removeValueChangeListener( valueChangeListener );
    }

    @Override
    public void addListener( Property.ValueChangeListener valueChangeListener ) {
        this.addValueChangeListener( valueChangeListener );
    }

    @Override
    public void removeListener( Property.ValueChangeListener valueChangeListener ) {
        this.removeValueChangeListener( valueChangeListener );
    }

    public void toggle( boolean editing ) {
        setCompositionRoot( editing ? editableComponent : readOnlyComponent );
    }

}
