package oracle.social.johtaja.model;

import java.io.Serializable;

import waggle.common.modules.user.infos.XUserInfo;
import waggle.common.modules.user.infos.XUserSearchInfo;
import waggle.core.id.XObjectID;


public class UserDataObject implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = -8284186935298262968L;
    
    private String userId;
    private String displayName;
    private String emailAddress;
    private Boolean enabled;
    private Boolean deleted;

    public UserDataObject() {
    }
    
    public UserDataObject(XUserInfo xuser) {
        this.userId = xuser.ID.toString();
        this.displayName = xuser.DisplayName;
        this.emailAddress = xuser.EMailAddress;
        this.enabled = xuser.Enabled;
        this.deleted = xuser.Deleted;
    }
    
    public UserDataObject(XUserSearchInfo xuser) {
        this.userId = xuser.ID.toString();
        this.displayName = xuser.DisplayName;
        this.emailAddress = xuser.EMailAddress;
        this.enabled = xuser.Enabled;
        this.deleted = xuser.Deleted;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public String getUserId() {
        return userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
