package ovh.serial30.s30api.pojos.request;

import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.exceptions.ArgumentInvalidEx;
import ovh.serial30.s30api.utilities.Util;

/**
 * User login data representation
 */
@Getter @Setter
public class UserLoginRequest {
    private String username;
    private String password;

    /**
     * Creates user login data representation
     * @param username User's username
     * @param password User's password
     * @throws ArgumentInvalidEx If some argument value is invalid
     */
    public UserLoginRequest(String username, String password) throws ArgumentInvalidEx {
        if (Util.invalidStr(username)) throw new ArgumentInvalidEx("username", UserLoginRequest.class.getName(), "constructor");
        if (Util.invalidStr(password)) throw new ArgumentInvalidEx("password", UserLoginRequest.class.getName(), "constructor");
        this.username = username;
        this.password = password;
    }
}
