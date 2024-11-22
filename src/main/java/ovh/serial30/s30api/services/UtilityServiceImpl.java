package ovh.serial30.s30api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ovh.serial30.s30api.repositories.UsersRepository;

import javax.crypto.SecretKey;
import java.util.UUID;
import java.util.function.Function;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String getRequestUserId() {
        var userId = getSubject(request.getHeader("Authorization"));
        return String.valueOf(userId);
    }

    private Object getSubject(String token) {
        var payload = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        Function<Claims, Object> subjectResolver = Claims::getSubject;
        return subjectResolver.apply(payload);
    }

    private SecretKey getSigningKey() {
        var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean userExists(UUID userId) {
        return usersRepository.existsById(userId);
    }

    @Override
    public boolean userExists(String username) {
        return usersRepository.findByUsername(username).isPresent();
    }
}
