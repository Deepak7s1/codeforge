package oracle.social.johtaja.carbon.action;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.CarbonMainLayout;
import oracle.social.johtaja.carbon.view.UsersModuleLayout;


public class NaviActor {
    private static final String CLASSNAME = NaviActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    Window window;
    
    public NaviActor(Window win) {
        this.window = win;
    }

    public Command showUsersModule() {
        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                MainApplication mapp = (MainApplication)window.getApplication();
                UsersModuleLayout usersModule;
                
                if (!mapp.hasUIReference(UsersModuleLayout.UI_USERS_LAYOUT_ID)) {
                    usersModule = new UsersModuleLayout();
                    mapp.addUIReference(UsersModuleLayout.UI_USERS_LAYOUT_ID, usersModule);
                }
                
                usersModule = (UsersModuleLayout)mapp.getUIReference(UsersModuleLayout.UI_USERS_LAYOUT_ID);
                VerticalLayout region = (VerticalLayout)mapp.getUIReference(CarbonMainLayout.UI_REGION_ID);
                logger.warning(">>>> showUsesModule: " + usersModule);
                region.addComponent(usersModule);
            }
        };
    }

}
