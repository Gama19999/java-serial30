package ovh.serial30.s30api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ovh.serial30.s30api.pojos.request.UserUpdate;
import ovh.serial30.s30api.utilities.Const;

@RestController
@RequestMapping(Const.Routes.USERS)
public class UserController {

    public ResponseEntity<?> updateUser(UserUpdate request) {
        return null;
    }
}
