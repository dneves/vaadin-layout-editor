package com.neon.vaadin.layout;

import com.neon.vaadin.upload.DesktopDropHandler;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

import java.util.Iterator;

public class OrderableVerticalLayoutDropHandler implements DropHandler {

    private static final String VERTICAL_LOCATION = "verticalLocation";

    private final DesktopDropHandler desktopDropHandler;

    private final OrderableVerticalLayout targetLayout;

    public OrderableVerticalLayoutDropHandler(OrderableVerticalLayout targetLayout) {
        this( targetLayout, null );
    }

    public OrderableVerticalLayoutDropHandler(OrderableVerticalLayout targetLayout, DesktopDropHandler desktopDropHandler ) {
        this.targetLayout = targetLayout;
        this.desktopDropHandler = desktopDropHandler;
    }

    @Override
    public void drop(DragAndDropEvent event) {
        Transferable transferable = event.getTransferable();
        TargetDetails dropTargetData = event.getTargetDetails();

        Component sourceComponent = transferable.getSourceComponent();
        AbstractComponentContainer parentSourceComponent = (AbstractComponentContainer) sourceComponent.getParent();

        boolean isDesktopDrop = ( transferable instanceof DragAndDropWrapper.WrapperTransferable
                && ( ( ( DragAndDropWrapper.WrapperTransferable ) transferable ).getFiles() != null ) );
        if ( isDesktopDrop ) {
            if (desktopDropHandler != null ) {
                int index = getDesktopDropIndex( dropTargetData.getTarget(), dropTargetData );
                // transferable is a WrapperTransferable and has files dragged from outside browser
                desktopDropHandler.handleDesktopDrop( ( DragAndDropWrapper.WrapperTransferable ) transferable, index );
            }
        } else {
            int index = getDropIndex(sourceComponent, dropTargetData);
            if (parentSourceComponent != targetLayout) {
                // items from outside layout
                if (parentSourceComponent instanceof OrderableVerticalLayout) {
                    if (((OrderableVerticalLayout) parentSourceComponent).allowRemoveFromSource()) {
                        parentSourceComponent.removeComponent(sourceComponent);
                    }
                }
                targetLayout.handle(sourceComponent, index);
            } else {
                // reorder items inside layout
                parentSourceComponent.removeComponent(sourceComponent);
                targetLayout.addComponent(sourceComponent, index);
            }
        }
    }

    private int getDesktopDropIndex( final Component targetComponent, TargetDetails targetDetails ) {
        int index = 0;
        Iterator<Component> iterator = targetLayout.iterator();
        Component next = null;
        while (next != targetComponent && iterator.hasNext()) {
            next = iterator.next();
            index++;
        }

        if ( targetDetails.getData( VERTICAL_LOCATION ).equals( VerticalDropLocation.MIDDLE.toString() ) ) {
            index--;
        }
        if ( targetDetails.getData( VERTICAL_LOCATION ).equals( VerticalDropLocation.TOP.toString() ) ) {
            index--;
        }

        return index < 0 ? 0 : index;
    }

    private int getDropIndex( Component sourceComponent, TargetDetails targetDetails ) {
        DropTarget targetComponent = targetDetails.getTarget();

        int index = 0;
        boolean sourceWasAfterTarget = true;

        Iterator<Component> iterator = targetLayout.iterator();
        Component next = null;
        while (next != targetComponent && iterator.hasNext()) {
            next = iterator.next();
            if (next != sourceComponent) {
                index++;
            } else {
                sourceWasAfterTarget = false;
            }
        }

        if (targetDetails.getData(VERTICAL_LOCATION).equals(VerticalDropLocation.MIDDLE.toString())) {
            if (sourceWasAfterTarget) {
                index--;
            }
        }

        if (targetDetails.getData(VERTICAL_LOCATION).equals(VerticalDropLocation.TOP.toString())) {
            index--;
            if ( index <= 0 ) {
                index = 0;
            }
        }

        return index;
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }

}
