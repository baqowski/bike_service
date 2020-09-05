package app.jwt.service;

import app.core.exception.ForbiddenException;
import app.core.service.helper.UserHelper;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static app.jwt.SecurityConstants.EXPIRATION_TIME;
import static app.jwt.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserHelper userHelper;
    private final AuthenticationManager authenticationManager;

    public ResponseJWT login(RequestJWT requestJWT) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestJWT.getUsername(), requestJWT.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Niepoprawna nazwa uzytkownika lub haslo. Sproboj ponownie");
        }
        String uuid = userHelper.getUserByUsername(requestJWT.getUsername()).getUuid();

        return new ResponseJWT(uuid, createTokenJWT(requestJWT.getUsername()), expirationToken().toString());
    }

    private String createTokenJWT(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    private Date expirationToken() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MILLISECOND, (int) EXPIRATION_TIME);
        return now.getTime();
    }

}
