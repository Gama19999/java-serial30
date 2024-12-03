package ovh.serial30.s30api.utilities;

import java.util.ArrayList;
import java.util.List;

public final class Const {
    private Const() {}
    public interface Logs {
        String $ = "\\{\\}";
        String active = "{} v{} is active!";
        interface Exceptions {
            String EXCEPTION_DETAIL = "s30-api exception thrown!\n\tType: {}\n\tStatus code: {}\n\tMessage: {}";
            String USER401 = "Wrong authentication credentials!";
            String USER404 = "User ({}) does not exist!";
            String USER422 = "User ({}) already exists!";
            String ARGUMENT422 = "Argument ({}) value invalid! at ({})";
            String PROJECT404 = "Project ({}) not found!";
            String PROJECT_INFO404 = "Project ({}) does not have info associated!";
            String ROLE404 = "Role ({}) not found!";
            String HEADER422 = "Header ({}) value invalid!";
            String TOKEN403 = "Token invalid!";
        }
        interface Authentication {
            String AUTH_SUCCESS = "Auth success for user ({})";
            String REGISTRATION_SUCCESS = "Registration success for user ({})";
        }
        interface Users {
            String REGISTERED = "User's registration successful! User ID: ({})";
            String CREATED = "ID: {} created!";
            String UPDATED = "User's update successful! User ID: ({})";
        }
        interface Token {
            String TOKEN_GENERATED = "Token generation successful!";
            String TOKEN_INFO = "Token information: userId({}) - expiration({})";
            String TOKEN_RENEW = "Token renew successful!";
        }
        interface Projects {
            String PROJECTS_LIST = "Successfully retrieved ({}) project previews!";
            String PROJECT_INFO = "Successfully retrieved project ({}) information!";
        }
    }
    public interface Routes {
        String ALL = "/**";
        /*---- SECURED ROUTES ----*/
        String RENEW = "/renew";
        String USERS = "/users";
        /*---- PUBLIC ROUTES ----*/
        String AUTH = "/authentication";
        String LOGIN = "/login";
        String REGISTER = "/register";
        String PUBLIC = "/public";
        String HOME = "/home";
        String PROJECTS = "/projects";
        String PROJECTS_INFO = "/{id}";
    }
    public interface Entities {
        String USERS = "users";
        String PROJECTS = "projects";
        String PROJECTS_INFO = "projects_info";
        String ROLES = "roles";
        interface Attributes {
            String ID = "id";
            String USER_ID = "user_id";
            String USERNAME = "username";
            String PASSWORD = "password";
            String PROJECT_ID = "project_id";
            String CREATED = "created_at";
            String MODIFIED = "modified_at";
            String FULL_NAME = "full_name";
            String SHORT_NAME = "short_name";
            String PROJECTS_INFO_ID = "projects_info_id";
            String DESCRIPTION = "description";
            String VERSION = "version";
            String PROJECT_URL = "project_url";
            String IMAGE_URL = "image_url";
            String ROLE_ID = "role_id";
            String NAME = "name";
        }
    }
    public static final ArrayList<String> ROLES = new ArrayList<>(List.of("user","editor","admin"));
}
