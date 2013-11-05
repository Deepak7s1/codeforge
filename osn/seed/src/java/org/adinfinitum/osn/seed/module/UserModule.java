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
import waggle.common.modules.user.updaters.XUserUpdater;
import waggle.core.api.XAPI;
import waggle.core.api.XAPIInputStream;
import waggle.core.id.XObjectID;


public class UserModule {
    private static final Logger logger = Logger.getLogger(UserModule.class.getName());
    static long PIC_COUNT = 0L;

    /**
     * Get the monotonically incrementing counter.
     * @return the pic count.
     */
    private long getIncrPicCount() {
        return PIC_COUNT++;
    }

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
     * Get current user info.
     * @param xapi XAPI
     */
    public XUserInfo getMe(XAPI xapi) {
        return xapi.call(XUserModule.Server.class).getMe();
    }


    /**
     * Grant a given user 'developer' privileges.
     * @param xapi XAPI
     * @param userId the given user ID
     */
    public void grantDeveloperRole(XAPI xapi, XObjectID userId) {
        XUserUpdater updater = new XUserUpdater();
        updater.put(XUserUpdater.DEVELOPER, Boolean.TRUE);
        xapi.call(XUserModule.Server.class).updateUser(userId, updater);
    }


    /**
     * Create a bunch of test users 'user1' ... 'userN' with 'waggle' for password.
     * @param xapi XAPI
     * @param initial the initial index
     * @param numUsers number of users to create
     */
    public void createTestUsers(XAPI xapi, int initial, int numUsers) {
        xapi.call(XUserModule.Server.class).createTestUsers("user", initial, numUsers);
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
     */
    public void createProfilePic(XAPI xapi, XObjectID userId) {
        try {
            // We only have 10 icons -- (id1.png to id10.png).
            long iconId = (getIncrPicCount() % 10) + 1;

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
