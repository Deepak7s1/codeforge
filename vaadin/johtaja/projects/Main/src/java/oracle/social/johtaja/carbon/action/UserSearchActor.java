package oracle.social.johtaja.carbon.action;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.UserModuleLayout;
import oracle.social.johtaja.carbon.view.UsersTable;
import oracle.social.johtaja.model.container.UsersContainer;

public class UserSearchActor implements Button.ClickListener {
    private static final String CLASSNAME = UserSearchActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    
    private MainApplication mapp;

    public UserSearchActor(MainApplication mapp) {
        this.mapp = mapp;
    }

    /**
     * Event handler when the Find User button is clicked.
     * @param clickEvent
     */
    public void buttonClick(Button.ClickEvent clickEvent) {
        TextField searchField = (TextField)mapp.getUIReference(UserModuleLayout.UI_FIND_PREFIX_FIELD_ID);
        String searchStr = searchField.getValue().toString().trim();
        
        UsersTable usersTable = (UsersTable)mapp.getUIReference(UsersTable.UI_TABLE_ID);
        UsersContainer dataSource = usersTable.getDataSource();
        dataSource.getUserList(searchStr);
    }
}
