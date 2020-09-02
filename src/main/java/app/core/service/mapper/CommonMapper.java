package app.core.service.mapper;

import app.core.entity.dto.OrderDTO;

/**
 * @author Karol Bąk
 */
public interface CommonMapper<T> {

    T map (OrderDTO orderDTO);
}
