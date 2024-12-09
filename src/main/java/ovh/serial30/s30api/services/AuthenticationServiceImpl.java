package ovh.serial30.s30api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;
import ovh.serial30.s30api.repositories.UsersRepository;
import ovh.serial30.s30api.utilities.Const;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UUID login(UserLoginRequest login) throws UserNotFoundEx {
        usersRepository.findByUsername(login.getUsername()).orElseThrow(() -> new UserNotFoundEx(login.getUsername()));
        var upat = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = authenticationManager.authenticate(upat);
        UUID userId = null;
        if (auth.isAuthenticated()) {
            logger.info(Const.Logs.Authentication.AUTH_SUCCESS, login.getUsername());
            userId = usersRepository.findByUsername(login.getUsername())
                                    .orElseThrow(() -> new UserNotFoundEx(login.getUsername()))
                                    .getId();
        }
        return userId;
    }
}
