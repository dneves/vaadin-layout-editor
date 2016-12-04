package com.neon.storyeditor.editor.component.impl;

import com.neon.storyeditor.DraggableComponent;
import com.neon.storyeditor.editor.action.StoryComponentAction;
import com.neon.storyeditor.editor.component.InlineTextEditor;
import com.neon.storyeditor.editor.component.StoryComponentWrapper;
import com.neon.storyeditor.editor.component.StoryEditorComponent;
import com.vaadin.ui.DragAndDropWrapper;


public class StoryTextComponent extends StoryEditorComponent< String, String > {

    private static final long serialVersionUID = 221710005716935498L;
    private final InlineTextEditor textEditor = new InlineTextEditor();

    final StoryComponentAction actionEdit = new StoryComponentAction( null, "editar", null ) {
        private static final long serialVersionUID = 411410720059703604L;

        @Override
        public void run() {
            toggle( true );
        }
    };
    final StoryComponentAction actionStopEdit = new StoryComponentAction( null, "guardar", null ) {
        private static final long serialVersionUID = -5135897716313524228L;

        @Override
        public void run() {
            toggle( false );
        }
    };


    private String model;

    private boolean isEditing;


    public StoryTextComponent(boolean isEditing) {
        toggle( isEditing );

        this.addStyleName( "text" );

        this.addComponent( textEditor );

        this.addAction( Position.RIGHT, actionEdit, 0 );
        this.addAction( Position.RIGHT, actionStopEdit, 0 );
    }


    private boolean isFilling = false;

    @Override
    public boolean isFilling() {
        return isFilling;
    }

    @Override
    public void update() {
        if (model != null){
            fill(model);
        }
    }

    @Override
    public void clear() {
        model = null;
        textEditor.clear();
    }

    @Override
    public void attach() {
        super.attach();
        toggle(isEditing);
    }

    @Override
    public void fill( String editModel ) {
        this.isFilling = true;

        clear();
        this.model = editModel;

        if ( model == null || model.trim().isEmpty() ) {
            return ;

        }

        textEditor.fill( model );

        this.isFilling = false;
    }

    @Override
    public String save() {
        return textEditor.save();
    }

    private void toggle( boolean editing ) {
        this.isEditing = editing;
        textEditor.toggle( editing );
        toggleLayoutClickListener(editing);
        actionEdit.setVisible( ! editing );
        actionStopEdit.setVisible( editing );

        if (getParent() instanceof StoryComponentWrapper){
            StoryComponentWrapper<?,?> parentWrapper = (StoryComponentWrapper<?,?>) getParent();
            DraggableComponent<?> draggable = parentWrapper.getPaletteSpacer().getParentDraggableComponent();
            draggable.setDragStartMode(editing ? DragAndDropWrapper.DragStartMode.NONE : DragAndDropWrapper.DragStartMode.COMPONENT);
        }
    }

}
