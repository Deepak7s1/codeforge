package oracle.social.johtaja.model.container;

import com.vaadin.data.util.BeanItemContainer;

import java.io.Serializable;
import java.util.logging.Logger;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.model.BCEventDataObject;


public class BCEventsContainer extends BeanItemContainer<BCEventDataObject> implements Serializable {
    private static final String CLASSNAME = BCEventsContainer.class.getName();
    private static final Logger logger = Logger.getLogger(CLASSNAME);
    
    /**
     * Natural property order for Person bean. Used in tables and forms.
     */
    public static final Object[] NATURAL_COL_ORDER =
        new Object[] { "eventName", "eventType", "eventDescription" };

    /**
     * "Human readable" captions for properties in same order as in
     * NATURAL_COL_ORDER.
     */
    public static final String[] COL_HEADERS_ENGLISH =
        new String[] { "Event Name", "Event Type", "Description" };


    private MainApplication mapp;
    
    public BCEventsContainer(MainApplication mapp) {
        super(BCEventDataObject.class);
        this.mapp = mapp;
    }
}
