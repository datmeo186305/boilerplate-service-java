package com.benit.team.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${spring.jwt.expired-time}")
    private Integer EXPIRE_TIME;

    public static final String USERNAME = "user_name";

    public String generateToken(String userName) throws JOSEException {
        JWSSigner jwsSigner = new MACSigner(SECRET_KEY.getBytes());
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.claim(USERNAME, userName);
        builder.expirationTime(generateExpirationDate());

        JWTClaimsSet jWTClaimsSet = builder.build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jWTClaimsSet);
        signedJWT.sign(jwsSigner);

        return signedJWT.serialize();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private JWTClaimsSet getClaimsFromToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        if (signedJWT.verify(verifier)) {
            return signedJWT.getJWTClaimsSet();
        }
        return null;
    }


    private Date getExpirationDateFromToken(String token) throws ParseException, JOSEException {
        JWTClaimsSet claims = getClaimsFromToken(token);
        if (claims == null)
            return new Date(0);
        return claims.getExpirationTime();
    }

    public String getUsernameFromToken(String token) throws ParseException, JOSEException {
        JWTClaimsSet claims = getClaimsFromToken(token);
        if (claims == null)
            return null;
        return claims.getStringClaim(USERNAME);
    }

    private Boolean isTokenExpired(String token) throws ParseException, JOSEException {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) throws ParseException, JOSEException {
        if (token == null || token.trim().length() == 0) {
            return false;
        }
        String username = getUsernameFromToken(token);
        if (username == null || username.isEmpty()) {
            return false;
        }

        return !isTokenExpired(token);
    }
}
