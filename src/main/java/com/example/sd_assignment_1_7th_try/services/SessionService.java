package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.User;
import com.example.sd_assignment_1_7th_try.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final String secret = "SUPER_SECRET_KEY";
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public boolean authenticate(Map<String, String> credentials) {
        User user = userRepository.findByEmail(credentials.get("email"));
        if(user == null) {
            return false;
        }

        return passwordEncoder.checkPassword(credentials.get("password"), user.getPassword());
    }


    public User isLoggedInAsAdmin() throws  AccessDeniedException, AuthenticationException {
        User currentUser = getCurrentUser();
        if (!currentUser.isAdmin())
            throw new AccessDeniedException("You are not authorized to access this resource");
        return currentUser;
    }

    public Cookie generateSessionCookie(String email){
        User user = userRepository.findByEmail(email);
        return new Cookie("_session_id", generateToken(user.getId().toString()));
    }

    private Long getUserIDFromCookie(Cookie[] cookies) throws AuthenticationException{
        // Get the cookie by name
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("_session_id")) {
                    String id = getUserIDFromToken(cookie.getValue());
                    return Long.parseLong(id);
                }
            }
        }

        throw new AuthenticationException("Please login!");
    }

    public User getCurrentUser() throws AuthenticationException {
        Long id = getUserIDFromCookie(request.getCookies());
        return userRepository.findUserById(id);
    }

    private String getUserIDFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String generateToken(String s) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, s);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
