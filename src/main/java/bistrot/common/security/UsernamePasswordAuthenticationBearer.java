package bistrot.common.security;

import com.nimbusds.jwt.SignedJWT;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UsernamePasswordAuthenticationBearer {

    private UsernamePasswordAuthenticationBearer() {}

    private static final String JWT_SCOPE = "scope";

    public static Mono<Authentication> create(SignedJWT signedJWT) {
        List authorities = getClaims(signedJWT).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return  Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(null, null, authorities));
    }

    private static List<String> getClaims(SignedJWT signedJWT) {
        try {
            return (List) signedJWT
                    .getJWTClaimsSet()
                    .getClaim(JWT_SCOPE);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }
}
