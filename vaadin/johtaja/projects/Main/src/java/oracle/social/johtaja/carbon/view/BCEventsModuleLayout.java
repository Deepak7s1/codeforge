package oracle.social.johtaja.carbon.view;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

import oracle.social.johtaja.app.MainApplication;


public class BCEventsModuleLayout extends HorizontalSplitPanel {
    public final static String UI_BCEVENTS_LAYOUT_ID = "BCEventsModuleLayout";

    private MainApplication mapp;

    public BCEventsModuleLayout(MainApplication mapp) {
        this.mapp = mapp;
        
        addStyleName(Reindeer.SPLITPANEL_SMALL);
        setSplitPosition(250, Sizeable.UNITS_PIXELS);
    }



}
