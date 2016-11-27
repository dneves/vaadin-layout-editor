package com.neon.vaadin.layout.editor.component;

import com.neon.dnd.Draggable;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.SourceComponentsHolder;
import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Columns extends VerticalLayout implements LayoutEditorComponent {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final HorizontalSplitPanel split = new HorizontalSplitPanel();

    private final Block left;

    private final Block right;


    protected Columns(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        this.left = new Block( editorViewFactory, sourceComponentsHolder );
        this.right = new Block( editorViewFactory, sourceComponentsHolder );

        VerticalLayout leftLayout = new VerticalLayout( left );
        leftLayout.setMargin( new MarginInfo( false, true, false, false ) );

        VerticalLayout rightLayout = new VerticalLayout( right );
        rightLayout.setMargin( new MarginInfo( false, false, false, true ) );

        split.setFirstComponent( leftLayout );
        split.setSecondComponent( rightLayout );
        split.setLocked( true );
//        split.setMinSplitPosition( 100 / 3, Unit.PERCENTAGE );
//        split.setMaxSplitPosition( 100 - ( 100 / 3 ), Unit.PERCENTAGE );

        setSpacing( true );

        Component settingsComponent = createSettingsComponent();

        HorizontalLayout upper = new HorizontalLayout();
        upper.addComponent( actions );
        upper.addComponent(settingsComponent);

        upper.setSizeFull();
        actions.setSizeUndefined();
        settingsComponent.setSizeUndefined();
        upper.setComponentAlignment( actions, Alignment.TOP_LEFT );
        upper.setComponentAlignment( settingsComponent, Alignment.MIDDLE_RIGHT );

        addComponent( upper );
        addComponent( split );

        addStyleName( "component-columns" );
    }

    private OptionGroup createOptions() {
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setNullSelectionAllowed( false );
        optionGroup.setHtmlContentAllowed( true );

        optionGroup.addValueChangeListener( listener -> {
            Property property = listener.getProperty();
            String value = String.valueOf( property.getValue() );
            if ( "left".equalsIgnoreCase( value ) ) {
                split.setSplitPosition( 67f, Unit.PERCENTAGE );
            } else if ( "right".equalsIgnoreCase( value ) ) {
                split.setSplitPosition( 33f, Unit.PERCENTAGE );
            } else {
                split.setSplitPosition( 50f, Unit.PERCENTAGE );
            }
        } );

        optionGroup.addItem( "left" );
        optionGroup.setItemCaption( "left", "67/33" );

        optionGroup.addItem( "middle" );
        optionGroup.setItemCaption( "middle", "50/50" );

        optionGroup.addItem( "right" );
        optionGroup.setItemCaption( "right", "33/67" );

        optionGroup.select( "middle" );
        return optionGroup;
    }

    private Component createSettingsComponent() {
        HorizontalLayout settings = new HorizontalLayout();
        settings.addComponent( createOptions() );

        return settings;
    }

    @Override
    public void addAction(Action action, Button.ClickListener listener ) {
        actions.addAction( action, listener  );
    }

    @Override
    public List< List<Draggable> > getModel() {
        return Arrays.asList( getModel( left ), getModel( right ) );
    }

    @Override
    public void setModel( List< List<Draggable> > draggables) {
        left.setModel(Collections.singletonList(draggables.get(0)));
        right.setModel(Collections.singletonList(draggables.get(1)));
    }

    private List< Draggable > getModel( Block block ) {
        List<List<Draggable>> models = block.getModel();
        return models.get( 0 );
    }

    public void setRemoveFromExternalSource( boolean removeFromExternalSource ) {
        this.left.setRemoveFromExternalSource( removeFromExternalSource );
        this.right.setRemoveFromExternalSource( removeFromExternalSource );
    }

    @Override
    public boolean isRemoveFromExternalSource() {
        return this.left.isRemoveFromExternalSource() && this.right.isRemoveFromExternalSource();
    }
}
