package oracle.social.johtaja.addressbook.ui;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

import oracle.social.johtaja.addressbook.data.Person;
import oracle.social.johtaja.addressbook.data.PersonContainer;
import oracle.social.johtaja.app.AddressBookTutorial;


public class PersonList extends Table {
    public PersonList(AddressBookTutorial app) {
        setSizeFull();
        setContainerDataSource(app.getDataSource());
        
        // Table column headers and order.
        setVisibleColumns(PersonContainer.NATURAL_COL_ORDER);
        setColumnHeaders(PersonContainer.COL_HEADERS_ENGLISH);

        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);

        /*
         * Make table selectable, react immediatedly to user events, and pass
         * events to the controller (our main application)
         */
        setSelectable(true);
        setImmediate(true);
        addListener((ValueChangeListener) app);
        /* We don't want to allow users to de-select a row */
        setNullSelectionAllowed(false);

        // customize email column to have mailto: links using column generator
        addGeneratedColumn("email", new ColumnGenerator() {
            public Component generateCell(Table source, Object itemId,
                    Object columnId) {
                Person p = (Person) itemId;
                Link l = new Link();
                l.setResource(new ExternalResource("mailto:" + p.getEmail()));
                l.setCaption(p.getEmail());
                return l;
            }
        });
    }
}
