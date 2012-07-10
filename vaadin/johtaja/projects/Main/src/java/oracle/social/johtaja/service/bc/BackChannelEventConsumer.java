package oracle.social.johtaja.service.bc;

import java.util.UUID;

import oracle.social.johtaja.model.BCEventDataObject;

public interface BackChannelEventConsumer {
    public UUID getConsumerId();
    public void handleEvent(BCEventDataObject eventObj);
}
