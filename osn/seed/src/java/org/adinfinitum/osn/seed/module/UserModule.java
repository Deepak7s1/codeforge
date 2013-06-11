package org.adinfinitum.osn.seed.module;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import waggle.common.modules.user.XUserModule;
import waggle.common.modules.user.infos.XUserInfo;
import waggle.core.api.XAPI;
import waggle.core.api.XAPIInputStream;
import waggle.core.id.XObjectID;


public class UserModule {
    private static final Logger logger = Logger.getLogger(UserModule.class.getName());
    private static final String WAGGLE_COLLECTION_EXTERNALID = "waggle.CollectionGadget";

    private static final UserModule _instance = new UserModule();
    private UserModule() {
    }

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static UserModule getInstance() {
        return _instance;
    }

    /**
     * Create a bunch of test users 'user1' ... 'userN' with 'waggle' for password.
     * @param xapi XAPI
     * @param numUsers number of users to create
     */
    public void createTestUsers(XAPI xapi, int numUsers) {
        xapi.call(XUserModule.Server.class).createTestUsers("user", 1, numUsers);
    }

    /**
     * Get the list of XUserInfo pertaining to the test users.
     * @param xapi XAPI
     * @param numUsers number of test users
     * @return the list of XUserInfo
     */
    public List<XUserInfo> getAllTestUsers(XAPI xapi, int numUsers) {
        List<XUserInfo> userList = new ArrayList<XUserInfo>(numUsers);
        for (int i = 1 ; i <= numUsers ; i++) {
            userList.add(xapi.call(XUserModule.Server.class).getUserByName("user" + i));
        }
        return userList;
    }

    /**
     * Create a profile picture for the given user. Identicons are used.
     * @param xapi XAPI
     * @param userId the given user ID
     * @param iconId to indicate the identicon to use (1 to 10).
     */
    public void createProfilePic(XAPI xapi, XObjectID userId, int iconId) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream("assets/identicon/id" + iconId + ".png");
            int length = getStreamLength(is);

            // Get the input stream instead of resetting it.
            is = cl.getResourceAsStream("assets/identicon/id" + iconId + ".png");
            XAPIInputStream xis = new XAPIInputStream(is, length);
            xapi.call(XUserModule.Server.class).uploadProfilePictureForUser(
                      userId, xis, "image/png", length);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }


    /**
     * Get the length of the input stream.
     * @param is InputStream
     * @return the length of the input stream.
     * @throws IOException
     */
    private int getStreamLength(InputStream is) throws IOException {
        int len;
        final int chunk_size = 1024;
        byte[] buf = new byte[chunk_size];

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = is.read(buf, 0, chunk_size)) != -1) {
            bos.write(buf, 0, len);
        }
        return bos.size();
    }

}
