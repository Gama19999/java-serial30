package ovh.serial30.s30api.services;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.serial30.s30api.entities.UserEntity;
import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.exceptions.UserWrongCredentialsEx;
import ovh.serial30.s30api.pojos.request.UserUpdate;
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
    private EntityManager entityManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public UUID registerUser(UserEntity entity) {
        usersRepository.save(entity);
        logger.info(Const.Logs.Users.REGISTERED, entity.getId());
        return entity.getId();
    }

    @Override
    @Transactional
    public String updateUserData(UserUpdate request) throws UserNotFoundEx, UserWrongCredentialsEx, RoleNotFoundEx, ProjectNotFoundEx {
        var userId = utilityService.getRequestUserId();
        var userOpt = usersRepository.findById(Util.touuid(userId));
        if (userOpt.isEmpty()) throw new UserNotFoundEx(userId);
        var userEnt = userOpt.get();
        if (invalidCurrentPassword(request, userEnt)) throw new UserWrongCredentialsEx();
        switch (request.getUpdateField()) {
            case 1 -> userEnt.setUsername(request.getNewValue());
            case 2 -> userEnt.setPassword(passwordEncoder.encode(request.getNewValue()));
            case 3 -> userEnt.setRoleId(rolesRepository.findByName(request.getNewValue())
                        .orElseThrow(() -> new RoleNotFoundEx(request.getNewValue()))
                        .getId());
            case 4 -> userEnt.setProjectId(projectsRepository.findById(Util.touuid(request.getNewValue()))
                        .orElseThrow(() -> new ProjectNotFoundEx(request.getNewValue()))
                        .getId());
        }
        usersRepository.save(userEnt);
        entityManager.refresh(userEnt);
        logger.info(Const.Logs.Users.UPDATED, userEnt.getId());
        return Util.tostr(userEnt.getId());
    }

    /**
     * Verifies if user current password sent in request is invalid
     * @param request User update data representation
     * @return {@code true} if current password sent in request is invalid. {@code false} otherwise.
     */
    private boolean invalidCurrentPassword(UserUpdate request, UserEntity userEnt) {
        return !passwordEncoder.matches(request.getCurrentPassword(), userEnt.getPassword());
    }

    @Override
    public UUID getIdByUsername(String username) throws UserNotFoundEx {
        var userEnt = usersRepository.findByUsername(username);
        if (userEnt.isEmpty()) throw new UserNotFoundEx(username);
        return userEnt.get().getId();
    }
}
