package com.exam.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    private static final String SECRET_KEY = "4f37bc12c4e50a60ae689fcbe5226f2941efb7b8eb549f11fddc0725257e8c99";
    private static final String JSON_FILE_PATH = "/serviceAccountKey.json";

    public static InputStream getResourceAsStream(String path) {
        return JwtUtils.class.getResourceAsStream(path);
    }



    public static PrivateKey getPrivateKeyFromJsonFile(String jsonFilePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, java.io.IOException {
        InputStream inputStream = getResourceAsStream(jsonFilePath);
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(inputStream);
        return ((ServiceAccountCredentials) credentials).getPrivateKey();
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public FirebaseToken verifyToken(String token) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email, String firebaseId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", firebaseId);
        return createToken(claims, email);
    }
    private String createToken(Map<String, Object> claims, String email) {
        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKeyFromJsonFile(JSON_FILE_PATH);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return (Date) extractClaim(token, claims -> claims.get("email").toString());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        String email = decodedToken.getEmail();
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public <T> Object extractClaim(String token, Function<Map<String, Object>, Object> claimsResolver){
//        FirebaseToken decodedToken = null;
//        try {
//            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
//        } catch (FirebaseAuthException e) {
//            throw new RuntimeException(e);
//        }
//        String uid = decodedToken.getUid();
//        Claims claims = (Claims) decodedToken.getClaims();
////        Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
        FirebaseToken decodedToken = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
        String uid = decodedToken.getUid();
        Map<String, Object> claims = decodedToken.getClaims(); // Lấy danh sách các claims từ FirebaseToken
        return claimsResolver.apply(claims);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
