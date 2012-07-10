package oracle.social.johtaja.model;

import java.io.Serializable;

public class BCEventDataObject implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 8317291399236116679L;
    
    private String eventName;
    private String eventType;
    private String eventDescription;
    
    public BCEventDataObject() {
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}
