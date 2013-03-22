package org.adinfinitum.osn.seed.config;

/**
 * A POJO used by Commons-Configuration to store settings
 * about how to connect to the OSN server.
 */
@ConfigIdentifier(name="connector")
public class Connector {

    private String serverUrl;
    private String webContextPath;
    private String adminUser;
    private String adminPassword;
    private String loginUser;
    private String loginPassword;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getWebContextPath() {
        return webContextPath;
    }

    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
