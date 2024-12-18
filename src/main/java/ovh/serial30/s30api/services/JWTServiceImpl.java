package ovh.serial30.s30api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.exceptions.TokenInvalidEx;
import ovh.serial30.s30api.exceptions.TokenNotRenewableEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.repositories.UsersRepository;
import ovh.serial30.s30api.utilities.Const;
import ovh.serial30.s30api.utilities.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;
import java.util.UUID;
import javax.crypto.SecretKey;

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
    private UsersRepository usersRepository;

    @Override
    public String generateToken(UUID userId) throws UserNotFoundEx {
        usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundEx(Util.tostr(userId)));
        var token = buildToken(userId);
        logger.info(Const.Logs.Token.TOKEN_GENERATED, Util.tostr(userId), new Date(System.currentTimeMillis() + expirationTime));
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
    public String renewToken(String token) throws UserNotFoundEx, TokenInvalidEx, TokenNotRenewableEx {
        var userId = handleGetExpiredTokenUserID(token);
        return generateToken(userId);
    }

    /**
     * Handles user's ID extract from expired token to perform token renew successfully
     * @param token User's expired token
     * @return User's ID
     * @throws TokenInvalidEx If token is an invalid string
     * @throws TokenNotRenewableEx If token is 7+ days expired
     */
    private UUID handleGetExpiredTokenUserID(String token) throws TokenInvalidEx, TokenNotRenewableEx {
        UUID userId;
        try {
            userId = Util.touuid(getClaim(token, Claims::getSubject).toString());
        } catch (ExpiredJwtException ex) {
            logger.info(Const.Logs.Token.TOKEN_RENEW, ex.getClaims().getSubject());
            var expiration = ex.getClaims().getExpiration();
            if (olderThan7Days(expiration)) throw new TokenNotRenewableEx();
            userId = Util.touuid(ex.getClaims().getSubject());
        }
        return userId;
    }

    /**
     * Verifies that token renewal for an expired token is not older than 7 days
     * @param tokenExp User's token expiration date
     * @return {@code true} if token is 7+ days expired - {@code false} otherwise
     */
    private boolean olderThan7Days(Date tokenExp) {
        var curCal = Calendar.getInstance();
        curCal.add(Calendar.DAY_OF_MONTH, -7);
        return Util.beforeThan(tokenExp, curCal.getTime());
    }

    @Override
    public UUID getUserID(String token) throws TokenInvalidEx {
        return Util.touuid(getClaim(token, Claims::getSubject).toString());
    }
}
