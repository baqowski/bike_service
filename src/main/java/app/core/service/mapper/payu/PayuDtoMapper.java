package app.core.service.mapper.payu;

import app.core.entity.Order;
import app.core.entity.dto.PayuDTO;

/**
 * @author Karol Bąk
 */
public interface PayuDtoMapper {

    PayuDTO map (Order order);


}
