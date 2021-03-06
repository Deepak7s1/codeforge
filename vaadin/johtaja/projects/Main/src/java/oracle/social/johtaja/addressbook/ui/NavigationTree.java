package oracle.social.johtaja.addressbook.ui;

import com.vaadin.ui.Tree;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import oracle.social.johtaja.app.AddressBookTutorial;


public class NavigationTree extends Tree {
    public static final Object SHOW_ALL = "Show all";
    public static final Object SEARCH = "Search";

    public NavigationTree(AddressBookTutorial app) {
        addItem(SHOW_ALL);
        addItem(SEARCH);

        setChildrenAllowed(SHOW_ALL, false);

        /*
         * We want items to be selectable but do not want the user to be able to
         * de-select an item.
         */
        setSelectable(true);
        setNullSelectionAllowed(false);

        // Make application handle item click events
        addListener((ItemClickListener) app);

    }
}
