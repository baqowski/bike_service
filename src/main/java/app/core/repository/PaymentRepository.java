package app.core.repository;

import app.core.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
