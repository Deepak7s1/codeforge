package oracle.social.johtaja.carbon.view;

import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalSplitPanel;

public class ListDetailView extends VerticalSplitPanel {
    
    public ListDetailView(Table listView, Form formView) {
        addStyleName("view");
        setFirstComponent(listView);
        setSecondComponent(formView);
        setSplitPosition(60);
    }
}
