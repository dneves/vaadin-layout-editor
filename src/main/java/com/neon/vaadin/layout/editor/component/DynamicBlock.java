package com.neon.vaadin.layout.editor.component;

import com.neon.layout.ActionsHorizontalLayout;
import com.neon.vaadin.layout.editor.Action;
import com.neon.vaadin.layout.editor.component.model.DynamicBlockModel;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicBlock extends VerticalLayout implements LayoutEditorComponent< DynamicBlockModel > {

    private final ActionsHorizontalLayout actions = new ActionsHorizontalLayout();

    private final HorizontalLayout layout = new HorizontalLayout();

    private final int maxBlocks;

    private final List< Block > blocks;

    private final Map< Block, AbstractSelect > selects;


    private final Button button = new Button( "", new ThemeResource( "icons/add-12x12.png" ) );


    DynamicBlock(int maxBlocks) {
        this.maxBlocks = maxBlocks;
        this.blocks = new ArrayList<>( maxBlocks );
        this.selects = new HashMap<>( maxBlocks );

        setSpacing( true );

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

        button.setEnabled( ( blocks.size() < maxBlocks ) );

        updateBlockModels();

        repaint();

        updateBlockOptions();
    }

    private void removeBlock( Block block ) {
        blocks.remove( block );
        selects.remove( block );

        button.setEnabled( ( blocks.size() < maxBlocks ) );

        updateBlockModels();
        updateBlockOptions();

        repaint();
    }

    private void updateBlockOptions() {
        int r = maxBlocks / blocks.size();
        int l = maxBlocks - blocks.size();

//        nr max opcoes = maxBlocks - blocks.size() + 1
//        E DEPOIS -> ajustar os restantes com o que sobra
//              quando aumentas 8====> roubar a diferenca à maior ( abs( nrColunasNovo - nrColunasAntigo ) )
//              quando diminuis 8=> dar ao mais pequeno (para nao ficar triste) a diferenca ( abs( nrColunasNovo - nrColunasAntigo ) )

        for ( int i = 0; i < blocks.size(); i++ ) {
            Block block = blocks.get(i);
            AbstractSelect select = selects.get(block);

            int nrColumns = r;
            if ( l <= 1 && i == ( blocks.size() - 1 ) ) {
                nrColumns = nrColumns + l;
            }

            select.select( nrColumns );
        }
    }

    private void updateBlockModels() {
        for ( int i = 0; i < blocks.size(); i++ ) {
            Block block = blocks.get(i);
            AbstractSelect select = selects.get(block);
            int nrColumns = select == null ? maxBlocks : ( select.getValue() == null ? maxBlocks : ( int ) select.getValue() );

            block.setModel( new Block.BlockModel( String.valueOf( i + 1 ), nrColumns) );
        }
    }


    private Block createBlock() {
        Block block = new Block();
//        block.setSizeFull();

        Action action = new Action();
        action.icon = new ThemeResource("icons/cancel-12x12.png");
        action.isVisible = true;
        action.listener = () -> {
            if ( blocks.size() > 1 ) {
                removeBlock(block);
            }
        };
        block.addAction( action );

        AbstractSelect blockOptions = createBlockOptions();
        selects.put( block, blockOptions );
        block.addSettings(blockOptions);

        return block;
    }

    private void repaint() {
        layout.removeAllComponents();
        blocks.forEach( block -> {
            AbstractSelect select = selects.get(block);
            int nrColumns = select == null ? maxBlocks : ( select.getValue() == null ? maxBlocks : ( int ) select.getValue() );

            layout.addComponent( block );
            layout.setExpandRatio( block, ( ( 100 / maxBlocks ) * nrColumns ) / 100.0f );
        } );
    }

    private Component createSettingsComponent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing( true );

        button.addStyleName( "action" );
        button.setWidth(24, Unit.PIXELS);
        button.setHeight(24, Unit.PIXELS);
        button.addClickListener( event -> addBlock( createBlock() ));

        layout.addComponent( button );

        return layout;
    }

    private AbstractSelect createBlockOptions() {
        ComboBox combo = new ComboBox();
        combo.setNullSelectionAllowed( false );
        combo.setInputPrompt( "colunas" );
        combo.setTextInputAllowed( false );
        combo.setItemCaptionMode( AbstractSelect.ItemCaptionMode.EXPLICIT );
        combo.addStyleName( "component-block-options" );
        combo.addValueChangeListener( listener -> {
            repaint();
        } );
        for ( int i = 0; i < maxBlocks; i++ ) {
            combo.addItem(i + 1 );
            combo.setItemCaption( i+1, "" + ( i + 1 ) );
        }
        combo.select( "" + maxBlocks );

        return combo;
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

        List<Block.BlockModel> blocks = model.getBlocks();
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
        List<Block.BlockModel> blockModels = new ArrayList<>( blocks.size() );
        blocks.forEach( block -> {
            AbstractSelect select = selects.get(block);
            Block.BlockModel model = block.getModel();
            model.nrColumns = ( int ) select.getValue();
            blockModels.add( model );
        } );

        DynamicBlockModel model = new DynamicBlockModel();
        model.setBlocks( blockModels );

        return model;
    }

}
