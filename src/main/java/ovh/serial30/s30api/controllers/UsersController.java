package ovh.serial30.s30api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ovh.serial30.s30api.exceptions.*;
import ovh.serial30.s30api.pojos.dto.MSExchange;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;
import ovh.serial30.s30api.pojos.request.UserUpdateRequest;
import ovh.serial30.s30api.services.JWTService;
import ovh.serial30.s30api.services.UsersService;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

@RestController
@RequestMapping(Const.Routes.USERS)
public class UsersController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UsersService usersService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody UserSignupRequest signup)
            throws ProjectNotFoundEx, RoleNotFoundEx, UserAlreadyExistsEx {
        var newUserId = Util.tostr(usersService.registerUser(signup));
        var response = new MSExchange(HttpStatus.CREATED.value(), newUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = Const.Routes.UPDATE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UserUpdateRequest update)
            throws TokenInvalidEx, UserWrongCredentialsEx, ProjectNotFoundEx, RoleNotFoundEx, UserNotFoundEx {
        var userId = jwtService.getUserID(token);
        var updUserResponse = usersService.updateUserData(userId, update);
        var response = new MSExchange(updUserResponse);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
