package ovh.serial30.s30api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ovh.serial30.s30api.pojos.response.MSResponse;
import ovh.serial30.s30api.utilities.Const;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GeneralAppException.class)
    public ResponseEntity<?> generalAppExceptions(GeneralAppException ex) {
        displayDetails(ex, ex.code, ex.getMessage());
        var response = new MSResponse(ex.code, ex.getMessage());
        return ResponseEntity.status(ex.code).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptions(BadCredentialsException ex) {
        displayDetails(ex, HttpStatus.UNAUTHORIZED.value(), Const.Logs.Exceptions.USER401);
        var response = new MSResponse(HttpStatus.UNAUTHORIZED.value(), Const.Logs.Exceptions.USER401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    private void displayDetails(Exception ex, int code, String message) {
        logger.error(Const.Logs.Exceptions.EXCEPTION_DETAIL, getExceptionName(ex), code, message);
        ex.printStackTrace();
    }

    private String getExceptionName(Exception ex) {
        var pkgs = ex.getClass().getName().split("\\.");
        return pkgs[pkgs.length - 1];
    }
}
