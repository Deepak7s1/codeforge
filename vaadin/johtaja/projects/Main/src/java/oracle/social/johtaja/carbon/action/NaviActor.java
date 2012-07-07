package oracle.social.johtaja.carbon.action;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.BCEventsModuleLayout;
import oracle.social.johtaja.carbon.view.CarbonMainLayout;
import oracle.social.johtaja.carbon.view.UserModuleLayout;


public class NaviActor {
    private static final String CLASSNAME = NaviActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    MainApplication mapp;
    
    public NaviActor(MainApplication mapp) {
        this.mapp = mapp;
    }


    public Command showBCEventsModule() {
        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                BCEventsModuleLayout moduleLayout;
                
                if (!mapp.hasUIReference(BCEventsModuleLayout.UI_BCEVENTS_LAYOUT_ID)) {
                    moduleLayout = new BCEventsModuleLayout(mapp);
                    mapp.addUIReference(BCEventsModuleLayout.UI_BCEVENTS_LAYOUT_ID, moduleLayout);
                }
                
                moduleLayout = (BCEventsModuleLayout)mapp.getUIReference(BCEventsModuleLayout.UI_BCEVENTS_LAYOUT_ID);
                VerticalLayout region = (VerticalLayout)mapp.getUIReference(CarbonMainLayout.UI_REGION_ID);
                region.removeAllComponents();
                region.addComponent(moduleLayout);
            }
        };
    }


    public Command showUsersModule() {
        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                UserModuleLayout moduleLayout;
                
                if (!mapp.hasUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID)) {
                    moduleLayout = new UserModuleLayout(mapp);
                    mapp.addUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID, moduleLayout);
                }
                
                moduleLayout = (UserModuleLayout)mapp.getUIReference(UserModuleLayout.UI_USERS_LAYOUT_ID);
                VerticalLayout region = (VerticalLayout)mapp.getUIReference(CarbonMainLayout.UI_REGION_ID);
                region.removeAllComponents();
                region.addComponent(moduleLayout);
            }
        };
    }
    
}
