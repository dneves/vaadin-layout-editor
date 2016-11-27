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

import java.util.ArrayList;
import java.util.List;

public class Columns extends VerticalLayout {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final EditorViewFactory editorViewFactory;

    private final Block left;

    private final Block right;

    private final HorizontalSplitPanel split = new HorizontalSplitPanel();


    public Columns(EditorViewFactory editorViewFactory, SourceComponentsHolder sourceComponentsHolder ) {
        this.editorViewFactory = editorViewFactory;

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
        upper.setComponentAlignment( actions, Alignment.MIDDLE_LEFT );
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

    public void addAction(Action action, Button.ClickListener listener ) {
        actions.addAction( action, listener  );
    }

    public List<Draggable> getModel() {
        List<Draggable> leftModel = left.getModel();
        List<Draggable> rightModel = right.getModel();
        List< Draggable > result = new ArrayList<>( leftModel.size() + rightModel.size() );
        result.addAll( leftModel );
        result.addAll( rightModel );
        return result;
    }

}
