package org.adinfinitum.osn.seed.module;

import java.util.List;
import java.util.logging.Logger;

import waggle.common.modules.opensocial.XOSModule;
import waggle.common.modules.opensocial.infos.XOSGadgetInfo;
import waggle.core.api.XAPI;
import waggle.core.id.XObjectID;


public class XOSGadgetModule {
    private static final Logger logger = Logger.getLogger(XOSGadgetModule.class.getName());
    private static final String WAGGLE_COLLECTION_EXTERNALID = "waggle.CollectionGadget";

    private static final XOSGadgetModule _instance = new XOSGadgetModule();
    private XOSGadgetModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static XOSGadgetModule getInstance() {
        return _instance;
    }

    /**
     * Get the GadgetID of the OSN Collection Gadget.
     * @param xapi XAPI
     * @return the GadgetID of the OSN Collection Gadget
     */
    public XObjectID getCollectionGadgetId(XAPI xapi) {
        List<XOSGadgetInfo> gadgetInfos = xapi.call(XOSModule.Server.class).getAllAvailableGadgets();
        for (XOSGadgetInfo gadgetInfo : gadgetInfos) {
            String externalID = gadgetInfo.GadgetExternalID;
            if (externalID != null && externalID.equals(WAGGLE_COLLECTION_EXTERNALID)) {
                return gadgetInfo.ID;
            }
        }
        return null;
    }
}
