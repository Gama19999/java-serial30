package ovh.serial30.s30api.pojos.request;

import lombok.Getter;
import lombok.Setter;
import ovh.serial30.s30api.exceptions.ArgumentInvalidEx;
import ovh.serial30.s30api.utilities.Util;

/**
 * User registration data representation
 */
@Getter @Setter
public class UserRegistration {
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
    public UserRegistration(String username, String password, String role, String projectId) throws ArgumentInvalidEx {
        if (Util.invalidStr(username)) throw new ArgumentInvalidEx("username", UserRegistration.class.getName(), "constructor");
        if (Util.invalidStr(password)) throw new ArgumentInvalidEx("password", UserRegistration.class.getName(), "constructor");
        if (Util.invalidRole(role)) throw new ArgumentInvalidEx("role", UserRegistration.class.getName(), "constructor");
        if (Util.invalidStr(projectId)) throw new ArgumentInvalidEx("projectId", UserRegistration.class.getName(), "constructor");
        this.username = username.trim();
        this.password = password.trim();
        this.role = role;
        this.projectId = projectId;
    }
}
