package app.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Karol Bąk
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserAddress extends AddressSuperclass {

    @Id
    private Long id;

    @ManyToOne
    private User user;
}
