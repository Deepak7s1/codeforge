package org.adinfinitum.osn.seed.module;

import waggle.common.modules.contact.XContactModule;
import waggle.common.modules.contact.infos.XContactAddInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;

import java.util.logging.Logger;


public class ContactModule {
    private static final Logger logger = Logger.getLogger(ContactModule.class.getName());

    private static final ContactModule _instance = new ContactModule();
    private ContactModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static ContactModule getInstance() {
        return _instance;
    }

    /**
     * Add a contact to the current logged in user.
     * @param xapi XAPI
     * @param contactId the user ID of the contact to add
     */
    public void createContact(XAPI xapi, XObjectID contactId) {
        XContactAddInfo contactToAdd = new XContactAddInfo();
        contactToAdd.ContactID = contactId;
        contactToAdd.ContactSendInvitation = false;
        xapi.call(XContactModule.Server.class).addContact(contactToAdd);
    }
}
