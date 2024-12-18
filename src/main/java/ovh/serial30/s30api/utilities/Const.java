package ovh.serial30.s30api.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class Const {
    private Const() {}
    public interface Http {
        String DEV = "http://localhost:4200";
        String LOCAL = "http://127.0.0.1:[*]";
        String NET = "https://*.serial30.ovh";
        String MOB = "https://localhost";
    }
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
            String TOKEN401 = "Token not renewable! Login required!";
        }
        interface Authentication {
            String AUTH_SUCCESS = "Auth success for user ({})";
        }
        interface Users {
            String REGISTERED = "User's registration successful! Created user ID: ({})";
            String UPDATED = "User ({}) updated successfully!";
        }
        interface Token {
            String TOKEN_GENERATED = "Token for user ({}) generated successfully! Expiration: ({})";
            String TOKEN_RENEW = "Renewing token expired! User's ID ({})";
            String TOKEN_RENEWED = "Token renew successful!";
        }
        interface Projects {
            String PROJECTS_LIST = "Successfully retrieved ({}) project previews!";
            String PROJECT_INFO = "Successfully retrieved project ({}) information!";
        }
    }
    public interface Routes {
        String CONTEXT = "/api";
        String ALL = "/**";
        /*---- TOKEN SECURED ROUTES ----*/
        String RENEW = "/renew";
        String USERS = "/users";
        String UPDATE = "/update";
        /*---- PUBLIC ROUTES ----*/
        String STATUS = "/status";
        String AUTH = "/authentication";
        String PROJECTS = "/projects";
        String LOGIN = "/login";
        String REGISTER = "/register";
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
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
}
