package ovh.serial30.s30api.pojos.request;

import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.exceptions.ArgumentInvalidEx;
import ovh.serial30.s30api.utilities.Util;

/**
 * User registration data representation
 */
@Getter @Setter
public class UserSignupRequest {
    private String username;
    private String password;
    private String role;
    private String projectId;

    /**
     * Creates user registration data representation
     * @param username User's username
     * @param password User's password
     * @param role User's role
     * @param projectId Project (app) ID
     * @throws ArgumentInvalidEx If some argument value is invalid
     */
    public UserSignupRequest(String username, String password, String role, String projectId) throws ArgumentInvalidEx {
        if (Util.invalidStr(username)) throw new ArgumentInvalidEx("username", UserSignupRequest.class.getName(), "constructor");
        if (Util.invalidStr(password)) throw new ArgumentInvalidEx("password", UserSignupRequest.class.getName(), "constructor");
        if (Util.invalidRole(role)) throw new ArgumentInvalidEx("role", UserSignupRequest.class.getName(), "constructor");
        if (Util.invalidStr(projectId)) throw new ArgumentInvalidEx("projectId", UserSignupRequest.class.getName(), "constructor");
        this.username = username.trim();
        this.password = password.trim();
        this.role = role;
        this.projectId = projectId;
    }
}
