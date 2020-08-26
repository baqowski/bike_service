package app.jwt.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
public class ResponseJWT {

    @NotNull
    private String username;

    @NotNull
    private String token;

    private String roleName;

    @NotNull
    private LocalDateTime expireIn;

}
