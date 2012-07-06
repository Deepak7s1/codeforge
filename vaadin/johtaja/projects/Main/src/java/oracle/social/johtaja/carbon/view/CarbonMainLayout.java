package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;


public class CarbonMainLayout extends VerticalLayout {
    private static final String CLASSNAME = CarbonMainLayout.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    
    public final static String UI_REGION_ID = "VerticalLayout:region";
    
    VerticalLayout header;
    VerticalLayout region;
    VerticalLayout footer;

    public CarbonMainLayout(MainApplication mapp) {
        addStyleName("view");
        setSizeFull();  // stretch to cover whole window.
        
        addComponent(getHeader(mapp));
                
        addComponent(getAppRegion());
        setExpandRatio(getAppRegion(), 1);
        mapp.addUIReference(UI_REGION_ID, getAppRegion());
        
        addComponent(getFooter());
        setComponentAlignment(getFooter(), Alignment.BOTTOM_CENTER);
    }
    
    
    private VerticalLayout getHeader(MainApplication mapp) {
        header = new MainMenuBar(mapp);
        return header;
    }


    private VerticalLayout getAppRegion() {
        if (region == null) {
            region = new VerticalLayout();
            region.setSizeFull();
        }
        return region;
    }


    private VerticalLayout getFooter() {
        if (footer == null) {
            footer = new VerticalLayout();
            footer.addStyleName("footer");
            
            Label copyrightLabel = new Label("Copyright \u00A9 2012, Oracle Corporation");
            copyrightLabel.addStyleName("copyright");
            footer.addComponent(copyrightLabel);
            footer.setComponentAlignment(copyrightLabel, Alignment.MIDDLE_LEFT);
        }
        return footer;
    }
}
