package oracle.social.johtaja.carbon.action;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.view.ConnectSubWindow;
import oracle.social.johtaja.carbon.view.MainMenuBar;
import oracle.social.johtaja.service.ServerSession;


public class ConnectActor implements Button.ClickListener, FieldEvents.TextChangeListener {
    private static final String CLASSNAME = ConnectActor.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);

    private ConnectSubWindow dialogWin;

        
    public ConnectActor(ConnectSubWindow dialog) {
        this.dialogWin = dialog;
    }


    /**
     * Event handler when the connect button is clicked in the
     * {@link ConnectSubWindow}.
     * @param clickEvent
     */
    public void buttonClick(Button.ClickEvent clickEvent) {        
        MainApplication mapp = (MainApplication)dialogWin.getApplication();
        ServerSession serverSide = mapp.getServerSession();

        try {
            serverSide.configureConnection(dialogWin.getServerURL(), 
                                           dialogWin.getServerContextPath());
            serverSide.serviceLogin(dialogWin.getUsername(), dialogWin.getPassword());
            showConnectSuccessNotification(serverSide.getCurrentLoginUsername());
            dialogWin.getParent().removeWindow(dialogWin);

            MenuItem connectMenuItem = (MenuItem)mapp.getUIReference(MainMenuBar.UI_CONNECT_ID);
            connectMenuItem.setEnabled(false);

            MenuItem disconnectMenuItem = (MenuItem)mapp.getUIReference(MainMenuBar.UI_DISCONNECT_ID);
            disconnectMenuItem.setEnabled(true);
        }
        catch (Exception e) {
            showErrorNotification();
        }
    }


    /**
     * When text is entered in the input fields of the connect dialog.
     * @param textChangeEvent
     */
    public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
        dialogWin.getSubmitButton().setEnabled(true);
    }


    /**
     * Disconnect from server command behavior to be attached to 'Disconnect'
     * menu item.
     * @return Command
     */
    public Command disconnectCommand() {
        final MainApplication mapp = (MainApplication)dialogWin.getApplication();
        final ServerSession serverSide = mapp.getServerSession();
        final Window mainWin = dialogWin.getParent();

        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                serverSide.serviceLogout();
                Notification successMessage = new Notification("You are disconnected.");
                successMessage.setPosition(Notification.POSITION_BOTTOM_RIGHT);
                successMessage.setDelayMsec(2000);
                mainWin.showNotification(successMessage);
                
                MenuItem connectItem = (MenuItem)mapp.getUIReference(MainMenuBar.UI_CONNECT_ID);
                MenuItem disconnectItem = (MenuItem)mapp.getUIReference(MainMenuBar.UI_DISCONNECT_ID);
                connectItem.setEnabled(true);
                disconnectItem.setEnabled(false);
            }
        };
    }


    /**
     * Connect to server command behavior to be attached to 'Connect'
     * menu item.
     * @return Command
     */
    public Command connectCommand() {
        final Window dialog = dialogWin;
        final Window mainWin = dialogWin.getParent();

        return new Command() {
            public void menuSelected(MenuItem selectedItem) {
                mainWin.addWindow(dialog);
            }
        };
    }


    private void showConnectSuccessNotification(String userName) {
        Notification successMessage = new Notification("You are connected as " + userName + ".");
        successMessage.setPosition(Notification.POSITION_BOTTOM_RIGHT);
        successMessage.setDelayMsec(2000);
        dialogWin.getParent().showNotification(successMessage);
    }


    private void showErrorNotification() {
        Notification errorMessage = new Notification("Connection failed.", Notification.TYPE_ERROR_MESSAGE);
        errorMessage.setDescription("<br/>Check if server is up and running.");
        errorMessage.setPosition(Notification.POSITION_BOTTOM_RIGHT);
        errorMessage.setDelayMsec(2000);
        dialogWin.getParent().showNotification(errorMessage);
    }

}
