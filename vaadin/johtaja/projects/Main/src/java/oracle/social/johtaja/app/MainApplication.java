package oracle.social.johtaja.app;

import com.vaadin.Application;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;

import java.util.HashMap;
import java.util.logging.Logger;

import oracle.social.johtaja.carbon.action.ConnectActor;
import oracle.social.johtaja.carbon.action.NaviActor;
import oracle.social.johtaja.carbon.view.CarbonMainLayout;
import oracle.social.johtaja.carbon.view.ConnectSubWindow;
import oracle.social.johtaja.carbon.view.MainMenuBar;
import oracle.social.johtaja.service.ServerSession;
import oracle.social.johtaja.service.ServiceFactory;


public class MainApplication extends Application {
    private static final String CLASSNAME = MainApplication.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    
    private final static String APPNAME = "OSN Developer Tool";
    
    private ServerSession serverSession = null;
    private HashMap<String, Object> uiReg = new HashMap<String, Object>();

    @Override
    public void init() {
        setTheme("carbon");
        buildMainLayout();
        setApplicationBehavior();
    }
    

    private void buildMainLayout() {
        setMainWindow(new Window(APPNAME));

        CarbonMainLayout layout = new CarbonMainLayout(this);
        
        getMainWindow().setContent(layout);
        getMainWindow().addWindow(new ConnectSubWindow(this));
    }
    
    
    /**
     * Some UI behavior can only be attached to components after layout is built.
     */
    private void setApplicationBehavior() {
        MenuItem connectItem = (MenuItem)getUIReference(MainMenuBar.UI_CONNECT_ID);
        MenuItem disconnectItem = (MenuItem)getUIReference(MainMenuBar.UI_DISCONNECT_ID);
        ConnectSubWindow csw = (ConnectSubWindow)getUIReference(ConnectSubWindow.UI_SUBWINDOW_ID);
        MenuItem usersItem = (MenuItem)getUIReference(MainMenuBar.UI_USERS_ID);

        connectItem.setCommand(new ConnectActor(csw).connectCommand());
        disconnectItem.setCommand(new ConnectActor(csw).disconnectCommand());
        usersItem.setCommand(new NaviActor(this).showUsersModule());
    }
    
    
    /**
     * Get the server session associated with this application.
     * @return the ServerSession instance.
     */
    public ServerSession getServerSession() {
        if (serverSession == null) {
            serverSession = new ServerSession();
        }
        return serverSession;
    }
    
    
    /**
     * Get the service factory associated with this application.
     * @return the ServiceFactory instance.
     */
    public ServiceFactory getServiceFactory() {
        return getServerSession().getServiceFactory();
    }
    
    
    
    public void addUIReference(String id, Object component) {
        uiReg.put(id, component);
    }


    public Object getUIReference(String id) {
        return uiReg.get(id);
    }
    
    
    public boolean hasUIReference(String id) {
        return uiReg.containsKey(id);
    }
}
