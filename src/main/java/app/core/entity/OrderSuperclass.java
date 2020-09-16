package app.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author Karol Bąk
 */
@MappedSuperclass
@Getter
@Setter
public abstract class OrderSuperclass {

    private LocalDateTime loanStart;

    private LocalDateTime loanTermination;

    private Integer loanDays;

    private String description;
}
