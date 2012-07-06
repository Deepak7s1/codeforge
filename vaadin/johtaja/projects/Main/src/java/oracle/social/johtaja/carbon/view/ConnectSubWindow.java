package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.action.ConnectActor;


public class ConnectSubWindow extends Window {
    public final static String DIALOG_TITLE = "Login to OSN Server";
    public final static String UI_SUBWINDOW_ID = "window:connect";
    
    VerticalLayout form;
    TextField username, serverURL, contextPath;
    PasswordField password;
    Button submitButton;


    public ConnectSubWindow(MainApplication mapp) {
        super(DIALOG_TITLE);
        mapp.addUIReference(UI_SUBWINDOW_ID, this);
        
        createDialog();
        setModal(true);
        setClosable(true);
        setDraggable(false);
        setResizable(false);
        addStyleName(Reindeer.WINDOW_LIGHT);
        center();
    }
    
    
    private void createDialog() {
        if (form == null) {
            form = (VerticalLayout) this.getContent();
            form.setSpacing(true);
            form.setMargin(true);
            form.setSizeUndefined();  // auto-size subwindow.
            
            // Username field + input prompt
            username = new TextField();
            username.setInputPrompt("Username");
            username.setValue("admin");
            username.setColumns(20);
            username.setImmediate(true);
            username.addListener(new ConnectActor(this));
            form.addComponent(username);

            // Password field + input prompt
            password = new PasswordField();
            password.setInputPrompt("Password");
            password.setValue("waggle");
            password.setColumns(20);
            password.setImmediate(true);
            password.addListener(new ConnectActor(this));
            form.addComponent(password);
            
            // Server URL field + input prompt
            serverURL = new TextField();
            serverURL.setInputPrompt("http://localhost:8080");
            serverURL.setValue("http://slc01gbx.us.oracle.com:7001");
            serverURL.setColumns(20);
            serverURL.setImmediate(true);
            serverURL.addListener(new ConnectActor(this));
            form.addComponent(serverURL);
            
            contextPath = new TextField();
            contextPath.setInputPrompt("/osn_context_path");
            contextPath.setValue("/osn");
            contextPath.setColumns(20);
            contextPath.setImmediate(true);
            contextPath.addListener(new ConnectActor(this));
            form.addComponent(contextPath);
            
            // Submit button            
            submitButton = new Button("Connect", new ConnectActor(this));
            submitButton.setEnabled(false);
            form.addComponent(submitButton);
            form.setComponentAlignment(submitButton, Alignment.BOTTOM_RIGHT);

        }
    }
    
    
    public String getUsername() {
        return username.getValue().toString().trim();
    }
    
    
    public String getPassword() {
        return password.getValue().toString().trim();
    }
    
    
    public String getServerURL() {
        return serverURL.getValue().toString().trim();
    }
    
    
    public String getServerContextPath() {
        return contextPath.getValue().toString().trim();
    }
    
    
    public Button getSubmitButton() {
        return submitButton;
    }

}
