package com.neon.vaadin.layout.editor.component;

import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.ColumnsComponentModel;
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

public class Columns extends VerticalLayout implements LayoutEditorComponent<ColumnsComponentModel> {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final HorizontalSplitPanel split = new HorizontalSplitPanel();

    private final Block left;

    private final Block right;

    private final OptionGroup optionGroup = new OptionGroup();


    Columns( EditorViewFactory editorViewFactory ) {
        this.left = new Block( editorViewFactory );
        this.right = new Block( editorViewFactory );

        VerticalLayout leftLayout = new VerticalLayout( left );
        leftLayout.setMargin( new MarginInfo( false, true, false, false ) );

        VerticalLayout rightLayout = new VerticalLayout( right );
        rightLayout.setMargin( new MarginInfo( false, false, false, true ) );

        split.setFirstComponent( leftLayout );
        split.setSecondComponent( rightLayout );
        split.setLocked( true );

        setSpacing( true );

        Component settingsComponent = createSettingsComponent();

        HorizontalLayout upper = new HorizontalLayout();
        upper.addComponent( actions );
        upper.addComponent(settingsComponent);

        upper.setSizeFull();
        actions.setSizeUndefined();
        settingsComponent.setSizeUndefined();
        upper.setComponentAlignment( actions, Alignment.TOP_LEFT );
        upper.setComponentAlignment( settingsComponent, Alignment.TOP_RIGHT );

        addComponent( upper );
        addComponent( split );

        addStyleName( "component-columns" );
    }

    private OptionGroup createOptions() {
        optionGroup.setNullSelectionAllowed( false );
        optionGroup.setHtmlContentAllowed( true );
        optionGroup.addStyleName( "size" );

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
    public void setModel(ColumnsComponentModel model) {
        optionGroup.select( model.getSizes() );

        BlockComponentModel column1 = model.getColumn1();
        left.setModel( column1 );

        BlockComponentModel column2 = model.getColumn2();
        right.setModel( column2 );
    }

    @Override
    public ColumnsComponentModel getModel() {
        ColumnsComponentModel model = new ColumnsComponentModel();
        model.setSizes( optionGroup.getValue() );
        model.setColumn1( left.getModel() );
        model.setColumn2( right.getModel() );

        return model;
    }

}
