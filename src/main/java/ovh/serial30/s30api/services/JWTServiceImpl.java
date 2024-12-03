package ovh.serial30.s30api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.exceptions.TokenInvalidEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTServiceImpl.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long expirationTime;
    @Value("${app.name}")
    private String appName;
    
    @Autowired
    private UtilityService utilityService;

    @Override
    public String generateToken(UUID userId) throws UserNotFoundEx {
        if (!utilityService.userExists(userId)) throw new UserNotFoundEx(Util.tostr(userId));
        var token = buildToken(userId);
        logger.info(Const.Logs.Token.TOKEN_GENERATED);
        logger.info(Const.Logs.Token.TOKEN_INFO, Util.tostr(userId), new Date(System.currentTimeMillis() + expirationTime));
        return token;
    }

    /**
     * Generates a new Json Web Token for user
     * @param userId User's ID
     * @return Json Web Token
     */
    private String buildToken(UUID userId) {
        return Jwts.builder()
                .subject(Util.tostr(userId))
                .issuer(appName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtains claim from token
     * @param token User token
     * @param claimResolver Claim to retrieve
     * @return Claim object
     * @throws TokenInvalidEx If token is an invalid string
     */
    private Object getClaim(String token, Function<Claims, Object> claimResolver) throws TokenInvalidEx {
        if (Util.invalidStr(token)) throw new TokenInvalidEx();
        var payload = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return claimResolver.apply(payload);
    }

    @Override
    public boolean validateToken(String token) throws TokenInvalidEx, UserNotFoundEx {
        if (!utilityService.userExists(Util.touuid(getClaim(token, Claims::getSubject).toString())))
            throw new UserNotFoundEx(getClaim(token, Claims::getSubject).toString());
        return String.valueOf(getClaim(token, Claims::getIssuer)).equals(appName) && nonExpired(token);
    }

    /**
     * Checks whether token is expired
     * @param token User token
     * @return {@code true} if token is expired. {@code false} otherwise
     * @throws TokenInvalidEx If token is invalid
     */
    private boolean nonExpired(String token) throws TokenInvalidEx {
        return !Util.beforeThan(getClaim(token, Claims::getExpiration), new Date());
    }

    @Override
    public String renewToken(String token) throws UserNotFoundEx, TokenInvalidEx {
        if (olderThanOneMonth(token)) throw new TokenInvalidEx();
        var userId = Util.touuid(getClaim(token, Claims::getSubject).toString());
        return generateToken(userId);
    }

    /**
     * Verifies token renewal is not older than one month
     * @param token Expired user's token
     * @return {@code true} if token is 1+ month older - {@code false} otherwise
     * @throws TokenInvalidEx If token is invalid
     */
    private boolean olderThanOneMonth(String token) throws TokenInvalidEx {
        var curCal = Calendar.getInstance();
        curCal.roll(Calendar.MONTH, -1);
        return Util.beforeThan(getClaim(token, Claims::getExpiration), curCal.getTime());
    }
}
