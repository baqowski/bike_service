package app.jwt.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
public class ResponseJWT {

    @NotNull
    private String uuid;

    @NotNull
    private String token;

    @NotNull
    private String expireIn;

}
