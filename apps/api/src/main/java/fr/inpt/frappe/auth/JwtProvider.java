package fr.inpt.frappe.auth;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

import fr.inpt.frappe.models.User;
import fr.inpt.frappe.repositories.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class JwtProvider {

	@Value("${JWT_SECRET}")
	private String jwtSecret;

	@Value("${JWT_EXPIRATION_MS}")
	private int jwtExpirationInMs;

	@Autowired
	private UserRepository users;

	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@PostConstruct
	public void init() throws JOSEException {
	}

	public String generateToken(Authentication authentication) {
		if (!(authentication.getPrincipal() instanceof AuthUser authUser)) {
			throw new IllegalArgumentException("Authentication principal is not an instance of AuthUser");
		}

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.issuer(JwtProvider.class.getPackageName())
				.subject(authentication.getName())
				.claim("user_info", authUser.getUserInfo().getClaims())
				.expirationTime(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);

		try {
			JWSSigner signer = new MACSigner(jwtSecret);
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			logger.error("Failed to sign JWT token: " + e.getMessage());
		}

		logger.debug("Generated JWT token: " + signedJWT.serialize());
		return signedJWT.serialize();
	}

	public boolean validateToken(String token) {
		try {
			JWSVerifier verifier = new MACVerifier(jwtSecret);
			SignedJWT signedJWT = SignedJWT.parse(token);
			logger.debug("Validating JWT token");
			return signedJWT.verify(verifier) &&
					new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
		} catch (JOSEException | ParseException ex) {
			logger.error("Invalid JWT token: " + ex.getMessage());
		}
		return false;
	}

	public AuthUser getUserFromJWT(String token) throws ParseException {
		SignedJWT signedJWT = SignedJWT.parse(token);
		JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

		User user = users.findByUid(claims.getSubject())
				.orElseThrow(() -> new IllegalArgumentException("Unauthorized"));

		logger.debug("JWT: user found, crafting AuthUser object");

		return new AuthUser(
				user,
				new DefaultOidcUser(
						List.of(),
						new OidcIdToken(token, null, null, claims.getClaims()),
						new OidcUserInfo(claims.getJSONObjectClaim("user_info"))));
	}
}
