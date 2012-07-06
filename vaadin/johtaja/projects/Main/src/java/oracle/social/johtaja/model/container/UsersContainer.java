package oracle.social.johtaja.model.container;

import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.model.UserDataObject;
import oracle.social.johtaja.service.UserModuleService;


public class UsersContainer extends BeanItemContainer<UserDataObject> implements Serializable {
    private static final String CLASSNAME = UsersContainer.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    /**
     * Natural property order for Person bean. Used in tables and forms.
     */
    public static final Object[] NATURAL_COL_ORDER =
        new Object[] { "userId", "displayName", "emailAddress", "enabled", "deleted" };

    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER.
     */
    public static final String[] COL_HEADERS_ENGLISH =
        new String[] { "XObject ID", "Display Name", "Email", "Is Enabled", "Is Deleted" };


    private MainApplication mapp;


    public UsersContainer(MainApplication mapp) {
        super(UserDataObject.class);
        this.mapp = mapp;
        getInitialData();
    }


    public void getInitialData() {
        List<UserDataObject> userList = getUserService().findAllUsers("u");
        for (UserDataObject user : userList) {
            addItem(user);
        }
    }


    public void getUserList(String searchStr) {
        // Clear the container first.
        removeAllItems();

        // Fetch new data into container.
        List<UserDataObject> userList = getUserService().findAllUsers(searchStr);
        for (UserDataObject user : userList) {
            addItem(user);
        }
        
        new BackgroundThread().start();
    }


    private UserModuleService getUserService() {
        return mapp.getServiceFactory().getUserModuleService();
    }


    public class BackgroundThread extends Thread {
        
        final int MAX_TIMES = 20;
        
        public BackgroundThread() {
            
        }


        @Override
        public void run() {
            // Simulate background work
            try {
                int i = 0;
                while (i < MAX_TIMES) {
                    logger.warning(">>> background thread "  + i);
                    Thread.sleep(2000);
                    UserDataObject udo = new UserDataObject();
                    udo.setUserId(String.valueOf(i + 10000));
                    udo.setDisplayName("testuser" + i);
                    udo.setEmailAddress("testuser" + i + "@example.com");
                    UsersContainer.this.addItem(udo);

                    i++;
                }
            }
            catch (InterruptedException e) {
                ; // ignore exception
            }
            

        }

    }
}
