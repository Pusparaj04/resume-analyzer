package com.resumeanalyzer.resume_analyzer.security;

import com.resumeanalyzer.resume_analyzer.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

//    convert the secret key into bytes using predictable Standard UTF-8 encoding (to prevent different value for
//    same key in different system, we use UTF-8)
//    later returns secret key generated using HS256 algorithm (Key -> HS256 algo -> Secret Key)
    private Key getSigningKey(){

        byte[] keyBytes = jwtProperties.
                getSecret().getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * JWT (JSON Web Token) consists of three parts:
     *      Header.Payload.Signature
     *
     * Header:
     *      Contains metadata such as the signing algorithm (alg) and token type (typ).
     *      We do not create it manually. Based on the algorithm passed to signWith(...),
     *      the JWT library automatically creates a header similar to:
     *          {
     *              "alg": "HS256",
     *              "typ": "JWT"
     *          }
     *
     * Payload:
     *      Contains claims (information about the token). Common registered claims are:
     *          - sub (subject)
     *          - iat (issued at)
     *          - exp (expiration time)
     *
     * JwtBuilder follows the Builder pattern. Each setter modifies the same builder
     * instance and returns it, allowing method chaining.
     *
     *      setSubject(...)   -> adds the subject claim
     *      setIssuedAt(...)  -> adds the issued time
     *      setExpiration(...) -> adds the expiration time
     *
     * signWith(...) does not immediately generate the signature. It only configures
     * the signing key and algorithm.
     *
     * When compact() is called, the library:
     *      1. Automatically creates the JWT header.
     *      2. Base64URL-encodes the header and payload.
     *      3. Generates the signature:
     *              HMACSHA256(header.payload, secretKey)
     *      4. Concatenates all three parts:
     *
     *          Base64Url(header) + "." +
     *          Base64Url(payload) + "." +
     *          Signature
     *
     * The generated JWT is returned as a String and is typically stored by the client
     * (e.g., browser) and sent with subsequent requests.
     *
     * Note:
     * JWT payload is only Base64URL-encoded, not encrypted. Anyone with the token can
     * decode and read its contents, so never store passwords or other sensitive
     * information inside the payload.
     *
     * If an attacker modifies the payload, they cannot generate a valid signature
     * without the secret key, so the server will reject the token during verification.
     */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Parses the JWT and verifies its signature using the secret key. If the token
     * is valid and not tampered with, it returns all claims (payload) as a Claims
     * object. This method acts as the base for extracting any information from the JWT.
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     * Generic method to extract any claim from the JWT. It first obtains the Claims
     * object using extractAllClaims(..), then applies the provided claimResolver
     * function (e.g., Claims::getSubject, Claims::getExpiration) to return the
     * required claim. This avoids writing separate extraction logic for each claim.
     */
    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Extracts the subject (username/email) from the JWT by delegating the work to
     * extractClaim(..) and passing Claims::getSubject as the resolver.
     */
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Extracts the token's issued time (iat) by calling extractClaim(..) with
     * Claims::getIssuedAt as the resolver.
     */
    public Date extractIssuedAt(String token){
        return extractClaim(token, Claims::getIssuedAt);
    }

    /*
     * Extracts the token's expiration time (exp) by calling extractClaim(..) with
     * Claims::getExpiration as the resolver.
     */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        boolean usernameMatch = (extractUserName(token).equals(userDetails.getUsername()));
        return (usernameMatch && !isTokenExpired(token));
    }
}
