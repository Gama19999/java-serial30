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
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLogin;
import ovh.serial30.s30api.pojos.request.UserRegistration;
import ovh.serial30.s30api.pojos.response.LoginResponse;
import ovh.serial30.s30api.pojos.response.MSResponse;
import ovh.serial30.s30api.services.AuthService;
import ovh.serial30.s30api.services.JWTService;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

@RestController
@RequestMapping(Const.Routes.AUTH)
public class AuthController {
    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    @Autowired
    private AuthService authService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserLogin login) throws UserNotFoundEx {
        var loggedUserId = authService.login(login);
        var userToken = jwtService.generateToken(loggedUserId);
        var response = new MSResponse(new LoginResponse(userToken, expirationTime));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserRegistration registration) throws ProjectNotFoundEx, RoleNotFoundEx {
        var newUserId = Util.tostr(authService.register(registration));
        var response = new MSResponse(HttpStatus.CREATED.value(), Const.Logs.Users.CREATED.replaceFirst(Const.Logs.$, newUserId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
