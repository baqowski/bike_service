package app.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

/**
 * @author Karol Bąk
 */
@MappedSuperclass
@Getter
@Setter
public abstract class UserSuperclass {

    private String firstName;

    private String lastName;

    private String phone;
}
