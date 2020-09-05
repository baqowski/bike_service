package app.core.repository;

import app.core.entity.Payment;
import app.core.entity.type.PaymentStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {

    Optional<Payment> findByOrder_Id(Long id);

    Optional<Payment> findByOrder_IdAndPaymentStatus(Long id, PaymentStatus paymentStatus);

}
