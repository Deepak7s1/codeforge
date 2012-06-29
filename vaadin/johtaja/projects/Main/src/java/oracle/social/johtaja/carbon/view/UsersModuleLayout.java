package oracle.social.johtaja.carbon.view;

import com.vaadin.terminal.Sizeable;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.themes.Reindeer;


public class UsersModuleLayout extends HorizontalSplitPanel {
    public final static String UI_USERS_LAYOUT_ID = "UsersModuleLayout";

    private Accordion accordion;

    public UsersModuleLayout() {
        addStyleName(Reindeer.SPLITPANEL_SMALL);
        setSplitPosition(200, Sizeable.UNITS_PIXELS);
        accordion = new Accordion();
        accordion.setSizeFull();
        accordion.addTab(new HorizontalLayout(), "Find By Name");
        accordion.addTab(new HorizontalLayout(), "Advanced Search");
        addComponent(accordion);
    }

}
