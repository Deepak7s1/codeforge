package oracle.social.johtaja.carbon.view;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.carbon.action.UserSearchActor;


public class UserModuleLayout extends HorizontalSplitPanel {
    public final static String UI_USERS_LAYOUT_ID = "UserModuleLayout";
    public final static String UI_FIND_PREFIX_FIELD_ID = "TextField:findUserNamePrefixField";

    private MainApplication mapp;
    
    private Accordion accordion;
    private VerticalLayout findPanel;
    private TextField findNamePrefixField;
    private Button findNamePrefixButton;
    
    private ListDetailView listDetail;
    private UsersTable usersTable;
    private UserDetailForm userForm;
    

    public UserModuleLayout(MainApplication mapp) {
        this.mapp = mapp;
        
        addStyleName(Reindeer.SPLITPANEL_SMALL);
        setSplitPosition(250, Sizeable.UNITS_PIXELS);
        
        accordion = new Accordion();
        accordion.setSizeFull();
        accordion.addTab(getFindPanel(), "Find By Name");
        accordion.addTab(new HorizontalLayout(), "Advanced Search");
        setFirstComponent(accordion);

        usersTable = new UsersTable(mapp);
        userForm = new UserDetailForm(mapp);
        listDetail = new ListDetailView(usersTable, userForm);
        setSecondComponent(listDetail);
    }
    
    
    public VerticalLayout getFindPanel() {
        if (findPanel == null) {
            findPanel = new VerticalLayout();
            findPanel.setSpacing(true);
            findPanel.setMargin(true);
            
            findNamePrefixField = new TextField("Enter Search Prefix");
            findNamePrefixField.setColumns(16);
            mapp.addUIReference(UI_FIND_PREFIX_FIELD_ID, findNamePrefixField);
            findPanel.addComponent(findNamePrefixField);
            
            findNamePrefixButton = new Button("Search", new UserSearchActor(mapp));
            findPanel.addComponent(findNamePrefixButton);
            
        }
        return findPanel;
    }

}
