package app.jwt;

/**
 * @author Karol Bąk
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 999999999; // 10 min
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String USERNAME = "user";
}
