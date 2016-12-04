package com.neon.vaadin.layout.editor.component;

import com.neon.vaadin.layout.editor.Action;
import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.EditorViewFactory;
import com.neon.vaadin.layout.editor.component.model.BlockComponentModel;
import com.neon.vaadin.layout.editor.component.model.DynamicBlockModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class DynamicBlock extends VerticalLayout implements LayoutEditorComponent< DynamicBlockModel > {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private static final int MAX_BLOCKS = 3;

    private static final int COLUMN_SETTINGS = 2;

    private static final String BIG = "big";
    private static final String SMALL = "small";


    private final HorizontalLayout layout = new HorizontalLayout();

    private List< Block > blocks = new ArrayList<>( MAX_BLOCKS );


    private final Button button = new Button( "", new ThemeResource( "icons/add-12x12.png" ) );

    private final OptionGroup optionGroup = new OptionGroup();


    private final EditorViewFactory editorViewFactory;


    public DynamicBlock( EditorViewFactory editorViewFactory ) {
        this.editorViewFactory = editorViewFactory;

        setSpacing( true );

        setupBlockOptions();
        Component settingsComponent = createSettingsComponent();

        HorizontalLayout upper = new HorizontalLayout();
        upper.setSizeFull();
        upper.addComponent( actions );
        upper.addComponent( settingsComponent );
        upper.setComponentAlignment( actions, Alignment.TOP_LEFT );
        upper.setComponentAlignment( settingsComponent, Alignment.TOP_RIGHT );

        actions.setSizeUndefined();
        layout.setSizeFull();
        layout.setSpacing( true );
        layout.addStyleName( "container" );

        addComponent( upper );
        addComponent( layout );

        addStyleName( "component-dynamic" );

        addBlock( createBlock() );
    }

    private void addBlock( Block block ) {
        blocks.add( block );

        button.setEnabled( ( blocks.size() < MAX_BLOCKS ) );
        optionGroup.setVisible( blocks.size() == COLUMN_SETTINGS );

        updateBlockSizes();

        repaint();
    }

    private void removeBlock( Block block ) {
        blocks.remove( block );

        button.setEnabled( ( blocks.size() < MAX_BLOCKS ) );
        optionGroup.setVisible( blocks.size() == COLUMN_SETTINGS );

        updateBlockSizes();

        repaint();
    }

    private void updateBlockSizes() {
        if ( blocks.size() != COLUMN_SETTINGS ) {
            blocks.forEach( b -> { b.removeStyleName( BIG ); b.removeStyleName( SMALL ); } );
        } else {
            String value = String.valueOf( optionGroup.getValue() );

            Block left = blocks.get(0);
            Block right = blocks.get(1);

            left.removeStyleName( BIG );
            left.removeStyleName( SMALL );
            right.removeStyleName( BIG );
            right.removeStyleName( SMALL );

            if ( "left".equalsIgnoreCase( value ) ) {
                left.addStyleName( BIG );
                right.addStyleName( SMALL );
            } else if ( "right".equalsIgnoreCase( value ) ) {
                left.addStyleName( SMALL );
                right.addStyleName( BIG );
            }
        }
    }

    private Block createBlock() {
        Block block = new Block( editorViewFactory );
        block.setSizeFull();

        Action action = new Action();
        action.icon = new ThemeResource("icons/cancel-12x12.png");
        action.isVisible = true;
        action.listener = () -> {
            if ( blocks.size() > 1 ) {
                removeBlock(block);
            }
        };
        block.addAction( action );

        return block;
    }

    private void repaint() {
        layout.removeAllComponents();
        blocks.forEach( layout::addComponent );
    }

    private Component createSettingsComponent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing( true );

        button.addStyleName( "action" );
        button.setWidth(24, Unit.PIXELS);
        button.setHeight(24, Unit.PIXELS);
        button.addClickListener( event -> addBlock( createBlock() ));

        layout.addComponent( optionGroup );
        layout.addComponent( button );

        return layout;
    }

    private void setupBlockOptions() {
        optionGroup.setNullSelectionAllowed( false );
        optionGroup.setHtmlContentAllowed( true );
        optionGroup.addStyleName( "size" );
        optionGroup.setVisible( blocks.size() == COLUMN_SETTINGS );

        optionGroup.addValueChangeListener( listener -> {
            if ( blocks.size() != COLUMN_SETTINGS ) {
                return ;
            }
            updateBlockSizes();
        } );

        optionGroup.addItem( "left" );
        optionGroup.setItemCaption( "left", "67/33" );

        optionGroup.addItem( "middle" );
        optionGroup.setItemCaption( "middle", "50/50" );

        optionGroup.addItem( "right" );
        optionGroup.setItemCaption( "right", "33/67" );

        optionGroup.select( "middle" );
    }

    @Override
    public void addAction(Action action) {
        actions.addAction( action );
    }

    @Override
    public void setModel(DynamicBlockModel model) {
        if ( model == null ) {
            return ;
        }

        List<BlockComponentModel> blocks = model.getBlocks();
        if ( blocks != null && ! blocks.isEmpty() ) {
            this.blocks.clear();
            this.layout.removeAllComponents();

            blocks.forEach( blockModel -> {
                Block block = createBlock();
                block.setModel( blockModel );

                addBlock( block );
            } );
        }
    }

    @Override
    public DynamicBlockModel getModel() {
        return null;
    }

}
