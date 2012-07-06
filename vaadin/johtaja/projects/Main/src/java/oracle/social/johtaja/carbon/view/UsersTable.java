package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.Table;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.action.UserManageActor;
import oracle.social.johtaja.model.container.UsersContainer;


public class UsersTable extends Table {
    public final static String UI_TABLE_ID = "Table:users";
    
    UsersContainer dataSource;
    
    public UsersTable(MainApplication mapp) {
        setSizeFull();
        
        dataSource = new UsersContainer(mapp);
        setContainerDataSource(dataSource);
        
        
        // Table column headers and order.
        setVisibleColumns(UsersContainer.NATURAL_COL_ORDER);
        setColumnHeaders(UsersContainer.COL_HEADERS_ENGLISH);
        
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);

        //
        // Make table selectable, react immediatedly to user events, and pass
        // events to the controller.
        //
        setSelectable(true);
        setImmediate(true);
        addListener((ValueChangeListener)new UserManageActor(mapp));
        
        
        // We don't want to allow users to de-select a row.
        setNullSelectionAllowed(false);
        
        mapp.addUIReference(UI_TABLE_ID, this);
    }


    public UsersContainer getDataSource() {
        return dataSource;
    }
}
