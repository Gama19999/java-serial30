package ovh.serial30.s30api.exceptions;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ovh.serial30.s30api.pojos.dto.MSExchange;
import ovh.serial30.s30api.utilities.Const;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles custom General App Exceptions
     * @param ex {@link GeneralAppException} object
     * @return MSResponse
     */
    @ExceptionHandler(GeneralAppException.class)
    public ResponseEntity<?> generalAppExceptions(GeneralAppException ex) {
        displayDetails(ex, ex.code, ex.getMessage());
        var response = new MSExchange(ex.code, ex.getMessage());
        return ResponseEntity.status(ex.code).body(response);
    }

    /**
     * Handles exception thrown when incorrect user credentials were used
     * @param ex {@link BadCredentialsException} object
     * @return MSResponse
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptions(BadCredentialsException ex) {
        displayDetails(ex, HttpStatus.UNAUTHORIZED.value(), Const.Logs.Exceptions.USER401);
        var response = new MSExchange(HttpStatus.UNAUTHORIZED.value(), Const.Logs.Exceptions.USER401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles exception thrown when <b>Authorization</b> header's value is invalid
     * @param ex {@link MissingRequestHeaderException} object
     * @return MSResponse
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    private ResponseEntity<?> missingHeaderExceptions(MissingRequestHeaderException ex) {
        var msg = Const.Logs.Exceptions.HEADER422.replaceFirst(Const.Logs.$, ex.getHeaderName());
        displayDetails(ex, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
        var response = new MSExchange(HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Handles exceptions thrown when:
     * <ul>
     *     <li>The jwt argument does not represent a signed Claims JWT</li>
     *     <li>The jwt string cannot be parsed or validated as required</li>
     *     <li>The jwt string is malformed</li>
     * </ul>
     * @param ex Exception thrown
     * @return MSResponse
     */
    @ExceptionHandler({UnsupportedJwtException.class, JwtException.class, IllegalArgumentException.class})
    private ResponseEntity<?> jwtParsingExceptions(Exception ex) {
        displayDetails(ex, HttpStatus.FORBIDDEN.value(), Const.Logs.Exceptions.TOKEN403);
        var response = new MSExchange(HttpStatus.FORBIDDEN.value(), Const.Logs.Exceptions.TOKEN403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Custom print of exception log
     * @param ex Exception thrown
     * @param code App error code
     * @param message Exception message
     */
    private void displayDetails(Exception ex, int code, String message) {
        logger.error(Const.Logs.Exceptions.EXCEPTION_DETAIL, getExceptionName(ex), code, message);
        ex.printStackTrace();
    }

    private String getExceptionName(Exception ex) {
        var pkgs = ex.getClass().getName().split("\\.");
        return pkgs[pkgs.length - 1];
    }
}
