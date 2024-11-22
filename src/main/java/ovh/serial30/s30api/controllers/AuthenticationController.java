package ovh.serial30.s30api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserAlreadyExistsEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;
import ovh.serial30.s30api.pojos.response.LoginResponse;
import ovh.serial30.s30api.pojos.response.MSResponse;
import ovh.serial30.s30api.services.AuthenticationService;
import ovh.serial30.s30api.services.JWTService;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

@RestController
@RequestMapping(Const.Routes.AUTH)
public class AuthenticationController {
    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserLoginRequest login) throws UserNotFoundEx {
        var loggedUserId = authenticationService.login(login);
        var userToken = jwtService.generateToken(loggedUserId);
        var response = new MSResponse(new LoginResponse(userToken, expirationTime));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserSignupRequest signup)
            throws ProjectNotFoundEx, RoleNotFoundEx, UserAlreadyExistsEx {
        var newUserId = Util.tostr(authenticationService.register(signup));
        var response = new MSResponse(HttpStatus.CREATED.value(), Const.Logs.Users.CREATED.replaceFirst(Const.Logs.$, newUserId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
