package oracle.social.johtaja.app;

import com.vaadin.Application;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.social.johtaja.carbon.action.ConnectActor;
import oracle.social.johtaja.carbon.action.NaviActor;
import oracle.social.johtaja.carbon.view.CarbonMainLayout;
import oracle.social.johtaja.carbon.view.ConnectSubWindow;
import oracle.social.johtaja.carbon.view.MainMenuBar;
import oracle.social.johtaja.service.ServerSession;
import oracle.social.johtaja.service.ServiceFactory;
import oracle.social.johtaja.service.bc.BackChannelEventConsumer;
import oracle.social.johtaja.service.bc.BackChannelEventsRegistry;


public class MainApplication extends Application 
                             implements Window.CloseListener {
    private static final String CLASSNAME = MainApplication.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    private static final Level DEBUG = Level.WARNING;
    
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
        // Close the application if the main window is closed.
        getMainWindow().addListener(this);
        final ConnectSubWindow csw = (ConnectSubWindow)getUIReference(ConnectSubWindow.UI_SUBWINDOW_ID);
        final ConnectActor connActor = new ConnectActor(csw);
        final NaviActor naviActor = new NaviActor(this);
        
        MenuItem connectItem = (MenuItem)getUIReference(MainMenuBar.UI_CONNECT_ID);
        MenuItem disconnectItem = (MenuItem)getUIReference(MainMenuBar.UI_DISCONNECT_ID);
        MenuItem bcEventsItem = (MenuItem)getUIReference(MainMenuBar.UI_BCEVENTS_ID);
        MenuItem usersItem = (MenuItem)getUIReference(MainMenuBar.UI_USERS_ID);

        connectItem.setCommand(connActor.connectCommand());
        disconnectItem.setCommand(connActor.disconnectCommand());
        bcEventsItem.setCommand(naviActor.showBCEventsModule());
        usersItem.setCommand(naviActor.showUsersModule());
        
        
        MenuItem exitItem = (MenuItem)getUIReference(MainMenuBar.UI_EXIT_ID);
        exitItem.setCommand(new Command() {
            public void menuSelected(MenuItem selectedItem) {
                connActor.disconnect(MainApplication.this);
                MainApplication.this.windowClose(null);
            }
        });

        // TODO: make configurable.
        setLogoutURL("https://cloud.oracle.com/mycloud/f?p=service:social:0");
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
    
    
    @Override
    public void windowClose(Window.CloseEvent e) {
        logger.log(DEBUG, "Closing the application.");
        deregisterBCEventConsumers();
        close();
    }
    
    
    private void deregisterBCEventConsumers() {
        for (Object uiObj : uiReg.values()) {
            if (uiObj instanceof BackChannelEventConsumer) {
                BackChannelEventConsumer consumer = (BackChannelEventConsumer)uiObj;
                BackChannelEventsRegistry.getInstance().removeEventConsumer(consumer.getConsumerId());
            }
        }
    }
    
}
