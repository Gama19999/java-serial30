package ovh.serial30.s30api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.serial30.s30api.exceptions.*;
import ovh.serial30.s30api.pojos.dto.MSExchange;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;
import ovh.serial30.s30api.pojos.response.LoginResponse;
import ovh.serial30.s30api.services.AuthenticationService;
import ovh.serial30.s30api.services.JWTService;
import ovh.serial30.s30api.utilities.Const;

import java.util.Date;

@RestController
@RequestMapping(Const.Routes.AUTH)
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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
        var expiration = Const.DATE_FORMAT.format(new Date(System.currentTimeMillis() + expirationTime));
        var response = new MSExchange(new LoginResponse(userToken, expiration));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = Const.Routes.RENEW,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> renew(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
            throws UserNotFoundEx, TokenInvalidEx, TokenNotRenewableEx {
        var userToken = jwtService.renewToken(token);
        var expiration = Const.DATE_FORMAT.format(new Date(System.currentTimeMillis() + expirationTime));
        var response = new MSExchange(new LoginResponse(userToken, expiration));
        logger.info(Const.Logs.Token.TOKEN_RENEW);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
