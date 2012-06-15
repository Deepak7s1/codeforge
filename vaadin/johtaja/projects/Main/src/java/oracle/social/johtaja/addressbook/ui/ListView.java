package oracle.social.johtaja.addressbook.ui;

import com.vaadin.ui.VerticalSplitPanel;


public class ListView extends VerticalSplitPanel {

    public ListView(PersonList personList, PersonForm personForm) {
        addStyleName("view");
        setFirstComponent(personList);
        setSecondComponent(personForm);
        setSplitPosition(40);
    }
}
