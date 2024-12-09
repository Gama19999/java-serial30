package ovh.serial30.s30api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.serial30.s30api.entities.UserEntity;
import ovh.serial30.s30api.exceptions.*;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;
import ovh.serial30.s30api.pojos.request.UserUpdateRequest;
import ovh.serial30.s30api.pojos.response.UserResponse;
import ovh.serial30.s30api.repositories.ProjectsRepository;
import ovh.serial30.s30api.repositories.RolesRepository;
import ovh.serial30.s30api.repositories.UsersRepository;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public UUID registerUser(UserSignupRequest request) throws UserAlreadyExistsEx, RoleNotFoundEx, ProjectNotFoundEx {
        if (usersRepository.findByUsername(request.getUsername()).isPresent())
            throw new UserAlreadyExistsEx(request.getUsername());
        var userEnt = new UserEntity();
        userEnt.setUsername(request.getUsername());
        userEnt.setPassword(passwordEncoder.encode(request.getPassword()));
        userEnt.setRoleId(rolesRepository.findByName(request.getRole())
                .orElseThrow(() -> new RoleNotFoundEx(request.getRole()))
                .getId());
        userEnt.setProjectId(projectsRepository.findById(Util.touuid(request.getProjectId()))
                .orElseThrow(() -> new ProjectNotFoundEx(request.getProjectId()))
                .getId());
        usersRepository.save(userEnt);
        logger.info(Const.Logs.Users.REGISTERED, userEnt.getId());
        return userEnt.getId();
    }

    @Override
    @Transactional
    public UserResponse updateUserData(UUID userId, UserUpdateRequest update)
            throws UserNotFoundEx, UserWrongCredentialsEx, RoleNotFoundEx, ProjectNotFoundEx {
        var userEnt = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundEx(Util.tostr(userId)));
        if (invalidCurrentPassword(update, userEnt)) throw new UserWrongCredentialsEx();
        switch (update.getUpdateField()) {
            case 1 -> userEnt.setUsername(update.getNewValue());
            case 2 -> userEnt.setPassword(passwordEncoder.encode(update.getNewValue()));
            case 3 -> userEnt.setRoleId(rolesRepository.findByName(update.getNewValue())
                        .orElseThrow(() -> new RoleNotFoundEx(update.getNewValue()))
                        .getId());
            case 4 -> userEnt.setProjectId(projectsRepository.findById(Util.touuid(update.getNewValue()))
                        .orElseThrow(() -> new ProjectNotFoundEx(update.getNewValue()))
                        .getId());
        }
        userEnt.updateModified();
        usersRepository.save(userEnt);
        logger.info(Const.Logs.Users.UPDATED, userEnt.getId());
        return new UserResponse(Util.tostr(userEnt.getId()), userEnt.getUsername(), userEnt.getModified());
    }

    /**
     * Verifies if user current password sent in request is invalid
     * @param request User update data representation
     * @return {@code true} if current password sent in request is invalid. {@code false} otherwise.
     */
    private boolean invalidCurrentPassword(UserUpdateRequest request, UserEntity userEnt) {
        return !passwordEncoder.matches(request.getPassword(), userEnt.getPassword());
    }
}
