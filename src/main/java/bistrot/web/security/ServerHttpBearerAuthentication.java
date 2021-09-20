package bistrot.web.security;

import bistrot.web.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * ServerHttpBearerAuthentication
 * for validate token and role
 *
 */

@RequiredArgsConstructor
public class ServerHttpBearerAuthentication implements Function<ServerWebExchange, Mono<Authentication>> {

    private static final String BEARER = "Bearer ";
    private final JwtUtil jwtVerifier;


    @Override
    public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {

        String authorization = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return Mono.justOrEmpty(authorization)
                .filter(authorizationHeader -> authorization.startsWith(BEARER))
                .filter(authorizationHeader -> authorization.length() > BEARER.length())
                .flatMap(authValue -> Mono.justOrEmpty(authValue.substring(BEARER.length())))
                .flatMap(this.jwtVerifier::isValidateToken)
                .flatMap(UsernamePasswordAuthenticationBearer::create).log();
    }


}
