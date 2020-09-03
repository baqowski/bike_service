package app.core.service.mapper;

import app.core.entity.OrderProduct;
import app.core.entity.User;
import app.core.entity.dto.ClientDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuProductDTO;

import java.util.List;

/**
 * @author Karol Bąk
 */
public interface PayuDtoMapper {

    PayuDTO map (Long orderId);

    ClientDTO map (User user);

    List<PayuProductDTO> map (List<OrderProduct> orderProducts);
}
