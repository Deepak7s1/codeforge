package oracle.social.johtaja.app;

import com.vaadin.Application;

import com.vaadin.terminal.Sizeable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import oracle.social.johtaja.addressbook.ui.ListView;
import oracle.social.johtaja.addressbook.ui.NavigationTree;
import oracle.social.johtaja.addressbook.ui.PersonForm;
import oracle.social.johtaja.addressbook.ui.PersonList;


public class AddressBookTutorial extends Application {
    // Application theme.
    private final static String THEME = "runo";

    // UI components.
    private Button newContact = new Button("Add contact");
    private Button search = new Button("Search");
    private Button share = new Button("Share");
    private Button help = new Button("Help");
    private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();
    private NavigationTree tree = new NavigationTree();
    private ListView listView = null;
    private PersonList personList = null;
    private PersonForm personForm = null;



    @Override
    public void init() {
        setTheme(THEME);
        buildMainLayout();
        setMainComponent(getListView());
    }


    private void buildMainLayout() {
        setMainWindow(new Window("Address Book Demo Application"));

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull(); // stretch to cover whole window.

        layout.addComponent(createToolbar());
        layout.addComponent(horizontalSplit);

        // Allocate all available extra space to the horizontal split panel.
        layout.setExpandRatio(horizontalSplit, 1);

        // Set the initial split position so we can have a 200 pixel menu to the left.
        horizontalSplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);

        // Add navigation tree to splitter.
        horizontalSplit.setFirstComponent(tree);


        getMainWindow().setContent(layout);

    }


    /**
     * Create the toolbar.
     * @return the HorizontalLayout
     */
    public HorizontalLayout createToolbar() {
        HorizontalLayout lo = new HorizontalLayout();
        lo.addComponent(newContact);
        lo.addComponent(search);
        lo.addComponent(share);
        lo.addComponent(help);
        return lo;
    }


    /**
     * Method to set view in main content area.
     * @param c the UI component to add to the main content area
     */
    private void setMainComponent(Component c) {
        horizontalSplit.setSecondComponent(c);
    }


    /**
     * Get the list view.
     * @return the ListView component
     */
    private ListView getListView() {
        // Lazy instantiation.
        if (listView == null) {
            personList = new PersonList();
            personForm = new PersonForm();
            listView = new ListView(personList, personForm);
        }
        return listView;
    }
}
