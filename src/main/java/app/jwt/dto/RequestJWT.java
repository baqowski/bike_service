package app.jwt.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestJWT {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
