package bistrot.common.security.utils;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

  @Value("${spring.security.jwt.secret}")
  private String defaultSecret;

  private JWSVerifier jwsVerifier;

  @PostConstruct
  public void init() {
    this.jwsVerifier = this.buildJWSVerifier();
  }

  private MACVerifier buildJWSVerifier() {
    try {
      return new MACVerifier(this.defaultSecret);
    } catch (JOSEException e) {
      log.error("Error build MACVerifier with secret, stack trace: {}", e);
      return null;
    }
  }

  public Mono<SignedJWT> isValidateToken(String token) {

    try {
      return Mono.justOrEmpty(createJWT(token))
              .filter(this::isTokenNotExpired)
              .filter(this::isValidSignature);
    } catch (final Exception e) {
      log.error("Error validate token, stack trace: {}", e.getStackTrace());
      return null;
    }
  }

  private SignedJWT createJWT(String token) throws ParseException {
    return SignedJWT.parse(token);
  }

  private boolean isTokenNotExpired(SignedJWT token) {
    try {
      return this.getExpirationDate(token).after(Date.from(Instant.now()));
    } catch (ParseException e) {
      log.error("Error parse token, stack trace {}", e);
      return false;
    }
  }

  private Date getExpirationDate(SignedJWT token) throws ParseException {
    return token.getJWTClaimsSet()
            .getExpirationTime();
  }

  private boolean isValidSignature(SignedJWT token) {
    // TODO verify signature whith passPhrase or public key
    return true;
  }
}
