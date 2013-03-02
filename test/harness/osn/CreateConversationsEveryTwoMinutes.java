import waggle.client.main.XClientMain;

import waggle.common.modules.connect.XConnectModule;
import waggle.common.modules.connect.infos.XLoginCredentialsInfo;
import waggle.common.modules.connect.infos.XLoginInfo;
import waggle.common.modules.conversation.XConversationModule;
import waggle.common.modules.hive.XHiveModule;
import waggle.common.modules.hive.infos.XHiveInfo;

import waggle.core.api.XAPI;
import waggle.core.api.XAPIManager;
import waggle.core.api.exceptions.XAPIException;
import waggle.core.exceptions.XRuntimeException;
import waggle.core.exceptions.infos.XExceptionInfo;
import waggle.core.id.XClientID;
import waggle.core.id.XObjectID;

/**
 * for i in {2..30}
 * do
 *   sleep 25 
 *   java -cp waggle-sdk/lib/waggle-sdk.jar:. CreateConversationsEveryTwoMinutes http://localhost:8080 ruby${i} waggle 10 &
 * done 
 */
public class CreateConversationsEveryTwoMinutes {
    public static void main(String[] args) throws Exception {
        if(args.length < 4) {
            System.err.println("Purpose: this is going to log in and create a conversation every 2 minutes.");
            System.err.println("Required Parameters: {base URL} {username} {password} {iterations}");
            System.exit(1);
        }


        XLoginCredentialsInfo loginCredentialsInfo = new XLoginCredentialsInfo();
        loginCredentialsInfo.UserName = args[1];
        loginCredentialsInfo.UserPassword = args[2];

        XClientMain.initApp();
        XAPI xapi = XAPIManager.newInstance();
        xapi.setSession(args[0], "/osn");
        XLoginInfo loginInfo = xapi.call(XConnectModule.Server.class).login(loginCredentialsInfo);
        xapi.setRandomID(loginInfo.APIRandomID);
        xapi.call(XHiveModule.Server.class).enterHive();
        for(int i = 0; i < Integer.parseInt(args[3]); i++) {
            xapi.call(XConversationModule.Server.class).createConversation(null, "Test conversation " + System.currentTimeMillis());
            try {
                Thread.sleep(120 * 1000);
            } catch(Exception e ) {
                System.out.println("heads-up: bailing out early from 2 minute sleep");
            }
        }
        xapi.call(XHiveModule.Server.class).exitHive();
        xapi.call(XConnectModule.Server.class).logout();
        XClientMain.shutdown();
    }
} 
