package oracle.social.johtaja.addressbook.ui;

import com.vaadin.ui.Tree;


public class NavigationTree extends Tree {
    public static final Object SHOW_ALL = "Show all";
    public static final Object SEARCH = "Search";

    public NavigationTree() {
        addItem(SHOW_ALL);
        addItem(SEARCH);
    }
}
