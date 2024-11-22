package ovh.serial30.s30api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.entities.UserEntity;
import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserAlreadyExistsEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;
import ovh.serial30.s30api.repositories.ProjectsRepository;
import ovh.serial30.s30api.repositories.RolesRepository;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UUID login(UserLoginRequest login) throws UserNotFoundEx {
        if (!utilityService.userExists(login.getUsername())) throw new UserNotFoundEx(login.getUsername());
        var upat = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = authenticationManager.authenticate(upat);
        UUID userId = null;
        if (auth.isAuthenticated()) {
            logger.info(Const.Logs.Authentication.AUTH_SUCCESS, login.getUsername());
            userId = usersService.getIdByUsername(login.getUsername());
        }
        return userId;
    }

    @Override
    public UUID register(UserSignupRequest signup) throws ProjectNotFoundEx, RoleNotFoundEx, UserAlreadyExistsEx {
        var userEnt = new UserEntity();
        userEnt.setUsername(signup.getUsername());
        userEnt.setPassword(passwordEncoder.encode(signup.getPassword()));
        userEnt.setRoleId(rolesRepository.findByName(signup.getRole())
                .orElseThrow(() -> new RoleNotFoundEx(signup.getRole()))
                .getId());
        userEnt.setProjectId(projectsRepository.findById(Util.touuid(signup.getProjectId()))
                .orElseThrow(() -> new ProjectNotFoundEx(signup.getProjectId()))
                .getId());
        var userId = usersService.registerUser(userEnt);
        logger.info(Const.Logs.Authentication.REGISTRATION_SUCCESS, userId);
        return userId;
    }
}
