package ovh.serial30.s30api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ovh.serial30.s30api.exceptions.TokenInvalidEx;
import ovh.serial30.s30api.utilities.Util;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${app.name}")
    private String appName;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (Util.isPublicRoute(request.getRequestURI())) {
            // PUBLIC ROUTES - Do not require token
            filterChain.doFilter(request, response);
        } else if (Util.invalidStr(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            // SECURED ROUTES - Require token - Block and Throw exception if Authorization header is absent or empty
            handlerExceptionResolver.resolveException(request, response, null, new TokenInvalidEx());
        } else {
            // SECURED ROUTES - Require token - Authorization header is present so validates token
            var tokenValidator = new TokenValidator(secretKey, appName);
            try {
                var valid = tokenValidator.validateToken(request.getHeader(HttpHeaders.AUTHORIZATION));
                if (valid) filterChain.doFilter(request, response);
                else handlerExceptionResolver.resolveException(request, response, null, new TokenInvalidEx());
            } catch (Exception ex) {
                handlerExceptionResolver.resolveException(request, response, null, ex);
            }
        }
    }

    /**
     * Handles token validation
     */
    private record TokenValidator(String secretKey, String appName) {
         /**
         * Validates token authenticity
         * @param token User's token
         * @return {@code true} if is a valid token - {@code false} otherwise
         * @throws TokenInvalidEx If token is invalid
         */
        public boolean validateToken(String token) throws TokenInvalidEx {
            return String.valueOf(getClaim(token, Claims::getIssuer)).equals(appName) && nonExpired(token);
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

        private SecretKey getSigningKey() {
            var keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
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
    }
}
