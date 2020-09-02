package app.core.service.mapper;

import app.core.entity.DeliveryAddress;
import app.core.entity.dto.OrderDTO;
import app.core.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@RequiredArgsConstructor
@Service
public class DeliveryAddressMapper implements CommonMapper<DeliveryAddress> {

    private final DeliveryAddressRepository deliveryAddressRepository;

    @Override
    public DeliveryAddress map(OrderDTO orderDTO) {
        return null;
    }

    public DeliveryAddress toDeliveryAddress (OrderDTO orderDTO){

        return DeliveryAddress.builder().build();
    }

}
