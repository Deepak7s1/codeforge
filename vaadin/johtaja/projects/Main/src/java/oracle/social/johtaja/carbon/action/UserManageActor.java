package oracle.social.johtaja.carbon.action;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.UserDetailForm;
import oracle.social.johtaja.carbon.view.UsersTable;


public class UserManageActor implements ValueChangeListener {
    private static final String CLASSNAME = UserManageActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    private MainApplication mapp;
    
    public UserManageActor(MainApplication mapp) {
        this.mapp = mapp;
    }


    public void valueChange(ValueChangeEvent valueChangeEvent) {
        Property property = valueChangeEvent.getProperty();
        if (property instanceof UsersTable) {
            // Get currently selected user.
            Item item = getUsersTable().getItem(getUsersTable().getValue());
            if (item != getUserForm().getItemDataSource()) {
                getUserForm().setItemDataSource(item);
            }
        }
    }
    
    
    private UsersTable getUsersTable() {
        return (UsersTable)mapp.getUIReference(UsersTable.UI_TABLE_ID);
    }
    
    
    private UserDetailForm getUserForm() {
        return (UserDetailForm)mapp.getUIReference(UserDetailForm.UI_FORM_ID);
    }
    
}
