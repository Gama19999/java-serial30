package ovh.serial30.s30api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.pojos.response.MSResponse;

@RestController
@RequestMapping(Const.Routes.PUBLIC + Const.Routes.HOME)
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Value("${app.name}")
    private String appName;
    @Value("${app.version}")
    private String appVersion;

    @RequestMapping(
            method = RequestMethod.GET,
            path = Const.Routes.EMPTY,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> status() {
        logger.info(Const.Logs.active, appName, appVersion);
        var data = Const.Logs.active.replaceFirst(Const.Logs.$, appName.toUpperCase()).replaceFirst(Const.Logs.$, appVersion);
        var response = new MSResponse(data);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
