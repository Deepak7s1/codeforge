package oracle.social.johtaja.carbon.action;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.CarbonMainLayout;
import oracle.social.johtaja.carbon.view.UserModuleLayout;


public class NaviActor {
    private static final String CLASSNAME = NaviActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    MainApplication mapp;
    
    public NaviActor(MainApplication mapp) {
        this.mapp = mapp;
    }

    public Command showUsersModule() {
        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                UserModuleLayout usersModule;
                
                if (!mapp.hasUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID)) {
                    usersModule = new UserModuleLayout(mapp);
                    mapp.addUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID, usersModule);
                }
                
                usersModule = (UserModuleLayout)mapp.getUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID);
                VerticalLayout region = (VerticalLayout)mapp.getUIReference(CarbonMainLayout.UI_REGION_ID);
                region.addComponent(usersModule);
            }
        };
    }

}
