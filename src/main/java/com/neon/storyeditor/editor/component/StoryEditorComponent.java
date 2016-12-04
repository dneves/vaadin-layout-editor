package com.neon.storyeditor.editor.component;

import com.neon.storyeditor.editor.action.StoryComponentAction;
import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class StoryEditorComponent< INPUT, OUTPUT > extends VerticalLayout implements IStoryEditorComponent< INPUT, OUTPUT > {

    private static final long serialVersionUID = 5170025566801610793L;

    private final HorizontalLayout layoutActions = new HorizontalLayout();

    private final HorizontalLayout layoutActionsLeft = new HorizontalLayout();

    private final HorizontalLayout layoutActionsRight = new HorizontalLayout();

    private final LayoutEvents.LayoutClickListener layoutClickListener = event -> {
        if ( event.getButton() == MouseEventDetails.MouseButton.LEFT && event.isDoubleClick() ) {
            doubleClick();
        } else if ( event.getButton() == MouseEventDetails.MouseButton.LEFT && ! event.isDoubleClick() ) {
            singleClick();
        }
    };

    private boolean changed = false;

    private boolean selected = false;

    public StoryEditorComponent() {
        this.setSizeFull();

        layoutActionsLeft.setSpacing( true );
        layoutActionsLeft.addStyleName( "left-actions-wrapper" );
        layoutActionsLeft.setSizeUndefined();
        layoutActionsRight.setSpacing( true );
        layoutActionsRight.addStyleName( "right-actions-wrapper" );
        layoutActionsRight.setSizeUndefined();
        layoutActions.addComponent( layoutActionsLeft );
        layoutActions.addComponent( layoutActionsRight );

        layoutActions.setSizeFull();
        layoutActions.addStyleName( "editor-component-actions-wrapper" );

        this.setSpacing( true );
        this.addComponent( layoutActions );

        this.addLayoutClickListener(layoutClickListener);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void addAction( Position position, StoryComponentAction action ) {
        AbstractOrderedLayout layout = getActionsLayout( position );
        addAction( layout, action, layout.getComponentCount() );
    }

    @Override
    public void addAction( Position position, StoryComponentAction action, int index ) {
        AbstractOrderedLayout layout = getActionsLayout( position );
        addAction( layout, action, index );
    }

    private void addAction( final AbstractOrderedLayout layout, final StoryComponentAction action, final int index ) {
        if ( layout == null ) {
            return ;
        }

        Component actionComponent = createActionComponent( action );

        layout.addComponent( actionComponent, index );
        layoutActions.setVisible( true );

        updateActionsState();
    }

    private AbstractOrderedLayout getActionsLayout( final Position position ) {
        switch ( position ) {
            case LEFT: return layoutActionsLeft;
            case RIGHT: return layoutActionsRight;
        }
        return null;
    }

    private Component createActionComponent( final StoryComponentAction action ) {
        final Button button = new Button( action.getLabel() );
        button.setData( action );
        button.setIcon( action.getIcon());
        button.setDescription( action.getDescription() );
        button.setStyleName( "transparent" );
        button.addClickListener( new Button.ClickListener() {
            private static final long serialVersionUID = -1080083614207673505L;

            @Override
            public void buttonClick( Button.ClickEvent event ) {
                action.run();
                updateActionsState();
            }
        });
        button.addStyleName( "story-editor-component-action" );
        return button;
    }

    private void updateActionsState() {
        updateActionsState( layoutActionsLeft );
        updateActionsState( layoutActionsRight );
    }

    private void updateActionsState( final ComponentContainer container ) {
        for ( Component actionComponent : container ) {
            if ( actionComponent instanceof AbstractComponent ) {
                Object data = ( ( AbstractComponent ) actionComponent ).getData();
                if ( data instanceof StoryComponentAction ) {
                    StoryComponentAction action = ( StoryComponentAction ) data;
                    actionComponent.setVisible( action.isVisible() );
                }
            }
        }
    }

    private void singleClick() {
        this.selected = ! this.selected;

        this.removeStyleName( "selected" );
        if ( this.selected ) {
            this.addStyleName( "selected" );
        }

        onSingleClick();
    }

    protected void onSingleClick() {

    }

    private void doubleClick() {
        onDoubleClick();
    }

    protected void onDoubleClick() {

    }

    public INPUT getUnsavedModel() {
        return null;
    }

    protected void toggleLayoutClickListener(boolean bolinho){
        if (bolinho){
            removeLayoutClickListener(layoutClickListener);
        }else{
            addLayoutClickListener(layoutClickListener);
        }

    }
}
