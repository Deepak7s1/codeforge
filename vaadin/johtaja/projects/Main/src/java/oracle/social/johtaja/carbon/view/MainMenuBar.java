package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import oracle.social.johtaja.app.MainApplication;


public class MainMenuBar extends VerticalLayout {
    public final static String UI_CONNECT_ID = "MenuItem:connect";
    public final static String UI_DISCONNECT_ID = "MenuItem:disconnect";
    public final static String UI_USERS_ID = "MenuItem:users";
    
    private MenuBar menubar = new MenuBar();


    public MainMenuBar(MainApplication mapp) {
        final MenuItem applicationMenu = menubar.addItem("Application", null);
        final MenuItem connectItem = applicationMenu.addItem("Connect to server...", null);
        mapp.addUIReference(UI_CONNECT_ID, connectItem);
        
        applicationMenu.addSeparator();
        final MenuItem disconnectItem = applicationMenu.addItem("Disconnect from server", null);
        mapp.addUIReference(UI_DISCONNECT_ID, disconnectItem);
        
        final MenuItem exploreMenu = menubar.addItem("Explore", null);
        final MenuItem usersItem = exploreMenu.addItem("Users", null);
        mapp.addUIReference(UI_USERS_ID, usersItem);        
        
        final MenuItem helpMenu = menubar.addItem("Help", null);
        helpMenu.addItem("About...", menuCommand);
        
        
        // Stretch the menu bar.
        menubar.setWidth("100%");
        addComponent(menubar);
        setWidth("100%");
    }
    
    
    private Command menuCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            getWindow().showNotification("Not Implemented: " + selectedItem.getText());
        }
    };

}
