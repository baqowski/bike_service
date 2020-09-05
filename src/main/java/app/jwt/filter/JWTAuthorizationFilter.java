package app.jwt.filter;

import app.core.service.JwtUserDetailService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.jwt.SecurityConstants.*;


/**
 * @author Karol Bąk
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUserDetailService jwtUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtUserDetailService jwtUserDetailService) {
        super(authManager);
        this.jwtUserDetailService = jwtUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(TOKEN_HEADER_NAME);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws InvalidClaimException {
        String token = request.getHeader(TOKEN_HEADER_NAME);
        if (token != null) {
            // parse the token.
            try {
                String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, jwtUserDetailService.loadUserByUsername(user).getAuthorities());
                }
            } catch (InvalidClaimException ex) {
                log.error(ex.getMessage());
            }

            return null;
        }
        return null;
    }

}
