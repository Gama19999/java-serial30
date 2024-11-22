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
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLogin;
import ovh.serial30.s30api.pojos.request.UserRegistration;
import ovh.serial30.s30api.repositories.ProjectsRepository;
import ovh.serial30.s30api.repositories.RolesRepository;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
    public UUID login(UserLogin login) throws UserNotFoundEx {
        if (!utilityService.userExists(login.getUsername())) throw new UserNotFoundEx(login.getUsername());
        var upat = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = authenticationManager.authenticate(upat);
        UUID userId = null;
        if (auth.isAuthenticated()) {
            logger.info(Const.Logs.Auth.AUTH_SUCCESS, login.getUsername());
            userId = usersService.getIdByUsername(login.getUsername());
        }
        return userId;
    }

    @Override
    public UUID register(UserRegistration registration) throws ProjectNotFoundEx, RoleNotFoundEx {
        var userEnt = new UserEntity();
        userEnt.setUsername(registration.getUsername());
        userEnt.setPassword(passwordEncoder.encode(registration.getPassword()));
        userEnt.setRoleId(rolesRepository.findByName(registration.getRole())
                .orElseThrow(() -> new RoleNotFoundEx(registration.getRole()))
                .getId());
        userEnt.setProjectId(projectsRepository.findById(Util.touuid(registration.getProjectId()))
                .orElseThrow(() -> new ProjectNotFoundEx(registration.getProjectId()))
                .getId());
        var userId = usersService.registerUser(userEnt);
        logger.info(Const.Logs.Auth.REGISTRATION_SUCCESS, userId);
        return userId;
    }
}
