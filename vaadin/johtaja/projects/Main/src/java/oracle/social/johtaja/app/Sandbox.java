package oracle.social.johtaja.app;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

public class Sandbox extends Application {
    
    @Override
    public void init() {
        Window mainWin = new Window("The Main Window");
        setMainWindow(mainWin);
        Button openButtonUI = new Button("Open", new OpenDialogListener(mainWin));
        mainWin.addComponent(openButtonUI);
    }
    
    
    public class OpenDialogListener implements Button.ClickListener {
        Window parentWin;
        
        public OpenDialogListener(Window win) {
            this.parentWin = win;
        }

        public void buttonClick(Button.ClickEvent clickEvent) {
            final Window dialog = new Window("Confirm Dialog");
            dialog.setPositionX(200);
            dialog.setPositionY(200);
            dialog.setHeight("300px");
            dialog.setWidth("400px");
            dialog.setModal(true);
            Button okButtonUI = new Button("OK", new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent clickEvent) {
                    parentWin.removeWindow(dialog);
                }
            });
            dialog.addComponent(okButtonUI);
                
            parentWin.addWindow(dialog);
        }
    }
}
