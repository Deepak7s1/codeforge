package oracle.social.johtaja.carbon.view;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;

import oracle.social.johtaja.app.MainApplication;


public class MainMenuBar extends VerticalLayout {
    public final static String UI_CONNECT_ID = "MenuItem:connect";
    public final static String UI_DISCONNECT_ID = "MenuItem:disconnect";
    public final static String UI_EXIT_ID = "MenuItem:exit";

    public final static String UI_MONITOR_ID = "MenuItem:monitorMenu";
    public final static String UI_BCEVENTS_ID = "MenuItem:bcEvents";

    public final static String UI_EXPLORE_ID = "MenuItem:exploreMenu";
    public final static String UI_USERS_ID = "MenuItem:users";

    public final static String UI_POLLER = "ProgressIndicator:poller";
    
    private MenuBar menubar = new MenuBar();


    public MainMenuBar(MainApplication mapp) {
        final CssLayout cssLayout = new CssLayout();
        cssLayout.setWidth("100%");
        
        Embedded logo = new Embedded("", new ThemeResource("images/osn-logo.png"));
        logo.setSizeUndefined();
        logo.setStyleName("logo");
                
        final CssLayout header_first = new CssLayout();
        header_first.setSizeUndefined();  // disable the default 100% width.
        header_first.setStyleName("header_first");
        cssLayout.addComponent(header_first);
        
        final Label header_title = new Label("Oracle Social - Developer Tool");
        header_title.setSizeUndefined();
        header_title.setStyleName("header_title");
        
        header_first.addComponent(logo);
        header_first.addComponent(header_title);
        
        final Label header_second = new Label();
        header_second.setSizeUndefined();  // disable the default 100% width.
        header_second.setStyleName("header_second");        
        cssLayout.addComponent(header_second);
        
        final CssLayout header_third = new CssLayout();
        header_third.setSizeUndefined();  // disable the default 100% width.
        header_third.setStyleName("header_third");        
        cssLayout.addComponent(header_third);
        
        final ProgressIndicator poller = new ProgressIndicator();
        poller.setIndeterminate(true);
        poller.setPollingInterval(2000);
        poller.setEnabled(false);
        poller.setVisible(false);
        poller.setStyleName("poller");
        mapp.addUIReference(UI_POLLER, poller);
        header_third.addComponent(poller);

        addComponent(cssLayout);
        
        //////////////////////
        // Application Menu.
        final MenuItem applicationMenu = menubar.addItem("Application", null);
        final MenuItem connectItem = applicationMenu.addItem("Connect to server...", null);
        mapp.addUIReference(UI_CONNECT_ID, connectItem);
        
        final MenuItem disconnectItem = applicationMenu.addItem("Disconnect from server", null);
        disconnectItem.setEnabled(false);
        mapp.addUIReference(UI_DISCONNECT_ID, disconnectItem);

        applicationMenu.addSeparator();

        final MenuItem exitItem = applicationMenu.addItem("Exit", null);
        mapp.addUIReference(UI_EXIT_ID, exitItem);


        //////////////////////
        // Monitor Menu.
        final MenuItem monitorMenu = menubar.addItem("Monitor", null);
        mapp.addUIReference(UI_MONITOR_ID, monitorMenu);
        
        final MenuItem bcEventsItem = monitorMenu.addItem("Backchannel Events", null);
        mapp.addUIReference(UI_BCEVENTS_ID, bcEventsItem);


        //////////////////////
        // Explore Menu.
        final MenuItem exploreMenu = menubar.addItem("Explore", null);
        mapp.addUIReference(UI_EXPLORE_ID, exploreMenu);
        
        final MenuItem usersItem = exploreMenu.addItem("Users", null);
        mapp.addUIReference(UI_USERS_ID, usersItem);        

        
        //////////////////////
        // Help Menu.
        final MenuItem helpMenu = menubar.addItem("Help", null);
        helpMenu.addItem("About...", aboutMenuCommand);
                
        // Stretch the menu bar.
        menubar.setWidth("100%");
        addComponent(menubar);
        setWidth("100%");
    }
    
    
    private Command aboutMenuCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            getWindow().addWindow(new AboutWindow());
        }
    };


    private Command notYetImplementedCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            getWindow().showNotification("Not Yet Implemented: " + selectedItem.getText());
        }
    };

}
