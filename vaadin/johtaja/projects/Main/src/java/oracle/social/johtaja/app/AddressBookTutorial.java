package oracle.social.johtaja.app;

import com.vaadin.Application;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import oracle.social.johtaja.addressbook.data.PersonContainer;
import oracle.social.johtaja.addressbook.data.SearchFilter;
import oracle.social.johtaja.addressbook.ui.HelpWindow;
import oracle.social.johtaja.addressbook.ui.ListView;
import oracle.social.johtaja.addressbook.ui.NavigationTree;
import oracle.social.johtaja.addressbook.ui.PersonForm;
import oracle.social.johtaja.addressbook.ui.PersonList;
import oracle.social.johtaja.addressbook.ui.SearchView;
import oracle.social.johtaja.addressbook.ui.SharingOptions;


public class AddressBookTutorial extends Application implements ClickListener, ValueChangeListener, ItemClickListener {
    // Application theme.
    private final static String THEME = "contacts";

    // UI components.
    private NavigationTree tree = new NavigationTree(this);

    private Button newContact = new Button("Add contact");
    private Button search = new Button("Search");
    private Button share = new Button("Share");
    private Button help = new Button("Help");
    private HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();

    // Lazily created UI references
    private ListView listView = null;
    private SearchView searchView = null;
    private PersonList personList = null;
    private PersonForm personForm = null;
    private HelpWindow helpWindow = null;
    private SharingOptions sharingOptions = null;

    // Data sources.
    private PersonContainer dataSource = PersonContainer.createWithTestData();


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

        search.addListener((ClickListener)this);
        share.addListener((ClickListener)this);
        help.addListener((ClickListener)this);
        newContact.addListener((ClickListener)this);

        search.setIcon(new ThemeResource("icons/32/folder-add.png"));
        share.setIcon(new ThemeResource("icons/32/users.png"));
        help.setIcon(new ThemeResource("icons/32/help.png"));
        newContact.setIcon(new ThemeResource("icons/32/document-add.png"));

        lo.setMargin(true);
        lo.setSpacing(true);

        lo.setStyleName("toolbar");

        lo.setWidth("100%");

        Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
        lo.addComponent(em);
        lo.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
        lo.setExpandRatio(em, 1);

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
            personList = new PersonList(this);
            personForm = new PersonForm(this);
            listView = new ListView(personList, personForm);
        }
        return listView;
    }


    /**
     * Get the search view.
     * @return the SearchView component
     */
    private SearchView getSearchView() {
        // Lazy instantiation.
        if (searchView == null) {
            searchView = new SearchView(this);
        }
        return searchView;
    }


    private HelpWindow getHelpWindow() {
        if (helpWindow == null) {
            helpWindow = new HelpWindow();
        }
        return helpWindow;
    }


    private SharingOptions getSharingOptions() {
        if (sharingOptions == null) {
            sharingOptions = new SharingOptions();
        }
        return sharingOptions;
    }


    /**
     * Get the data source/container to bind to table.
     * @return PersonContainer
     */
    public PersonContainer getDataSource() {
        return dataSource;
    }


    public void buttonClick(ClickEvent event) {
        final Button source = event.getButton();

        if (source == search) {
            showSearchView();
        }
        else if (source == help) {
            showHelpWindow();
        }
        else if (source == share) {
            showShareWindow();
        }
        else if (source == newContact) {
            addNewContanct();
        }
    }

    private void showHelpWindow() {
        getMainWindow().addWindow(getHelpWindow());
    }

    private void showShareWindow() {
        getMainWindow().addWindow(getSharingOptions());
    }

    private void showListView() {
        setMainComponent(getListView());
    }

    private void showSearchView() {
        setMainComponent(getSearchView());
    }

    public void valueChange(ValueChangeEvent event) {
        Property property = event.getProperty();
        if (property == personList) {
            Item item = personList.getItem(personList.getValue());
            if (item != personForm.getItemDataSource()) {
                personForm.setItemDataSource(item);
            }
        }
    }

    public void itemClick(ItemClickEvent event) {
        if (event.getSource() == tree) {
            Object itemId = event.getItemId();
            if (itemId != null) {
                if (NavigationTree.SHOW_ALL.equals(itemId)) {
                    // clear previous filters
                    getDataSource().removeAllContainerFilters();
                    showListView();
                }
                else if (NavigationTree.SEARCH.equals(itemId)) {
                    showSearchView();
                }
                else if (itemId instanceof SearchFilter) {
                    search((SearchFilter)itemId);
                }
            }
        }
    }

    private void addNewContanct() {
        showListView();
        personForm.addContact();
    }

    public void search(SearchFilter searchFilter) {
        // clear previous filters
        getDataSource().removeAllContainerFilters();
        // filter contacts with given filter
        getDataSource().addContainerFilter(searchFilter.getPropertyId(), searchFilter.getTerm(), true, false);
        showListView();

        getMainWindow().showNotification("Searched for " + searchFilter.getPropertyId() + "=*" +
                                         searchFilter.getTerm() + "*, found " + getDataSource().size() + " item(s).",
                                         Notification.TYPE_TRAY_NOTIFICATION);
    }

    public void saveSearch(SearchFilter searchFilter) {
        tree.addItem(searchFilter);
        tree.setParent(searchFilter, NavigationTree.SEARCH);
        // mark the saved search as a leaf (cannot have children)
        tree.setChildrenAllowed(searchFilter, false);
        // make sure "Search" is expanded
        tree.expandItem(NavigationTree.SEARCH);
        // select the saved search
        tree.setValue(searchFilter);
    }

}
