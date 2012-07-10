package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.Table;

import java.util.UUID;

import oracle.social.johtaja.app.MainApplication;

import oracle.social.johtaja.model.BCEventDataObject;

import oracle.social.johtaja.model.container.BCEventsContainer;
import oracle.social.johtaja.service.bc.BackChannelEventConsumer;
import oracle.social.johtaja.service.bc.BackChannelEventsRegistry;


public class BCEventsTable extends Table
                           implements BackChannelEventConsumer {
    public final static String UI_TABLE_ID = "Table:bcEvents";

    UUID tableID = UUID.randomUUID();
    BCEventsContainer dataSource;
    
    public BCEventsTable(MainApplication mapp) {
        setSizeFull();
        
        dataSource = new BCEventsContainer(mapp);
        setContainerDataSource(dataSource);
        
        
        // Table column headers and order.
        setVisibleColumns(BCEventsContainer.NATURAL_COL_ORDER);
        setColumnHeaders(BCEventsContainer.COL_HEADERS_ENGLISH);
   
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);

        //
        // Make table selectable, react immediatedly to user events, and pass
        // events to the controller.
        //
        setSelectable(true);
        setImmediate(true);

        // We don't want to allow users to de-select a row.
        setNullSelectionAllowed(false);
        
        mapp.addUIReference(UI_TABLE_ID, this);
        BackChannelEventsRegistry.getInstance().addEventConsumer(tableID, this);
    }
    
    
    public BCEventsContainer getDataSource() {
        return dataSource;
    }

    ///////////////////////////////////////////////////////////////////////////
    //// Implement BackChannelEventConsumer interface

    @Override
    public UUID getConsumerId() {
        return tableID;
    }

    @Override
    public void handleEvent(BCEventDataObject eventObj) {
        getDataSource().addItem(eventObj);
    }
}
