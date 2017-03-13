package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 8/16/16.
 */
public class Settings implements Serializable {
    String REMOTE_HOST;
    int REMOTE_PORT;
    String LocalUser;
    String LocalPassword;

    public String getLocalPassword() {
        return LocalPassword;
    }

    public void setLocalPassword(String localPassword) {
        LocalPassword = localPassword;
    }


    public String getREMOTE_HOST() {
        return REMOTE_HOST;
    }

    public void setREMOTE_HOST(String REMOTE_HOST) {
        this.REMOTE_HOST = REMOTE_HOST;
    }

    public int getREMOTE_PORT() {
        return REMOTE_PORT;
    }

    public void setREMOTE_PORT(int REMOTE_PORT) {
        this.REMOTE_PORT = REMOTE_PORT;
    }

    public String getLocalUser() {
        return LocalUser;
    }

    public void setLocalUser(String localUser) {
        LocalUser = localUser;
    }

}
