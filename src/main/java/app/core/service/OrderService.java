package app.core.service;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.Product;
import app.core.entity.User;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.ProductDTO;
import app.core.entity.type.OrderStatus;
import app.core.exception.ProductException;
import app.core.repository.OrderProductRepository;
import app.core.repository.OrderRepository;
import app.core.repository.ProductRepository;
import app.core.repository.UserRepository;
import app.core.service.helper.OrderHelper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderHelper orderHelper;

    @Transactional
    public Long create(List<ProductDTO> productDTOS) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
                /*.orElseThrow(() -> new UsernameNotFoundException("Cant find user"));
*/
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setUser(user);
        orderRepository.save(order);

        List<OrderProduct> orderProducts = toOrderProductList(productDTOS, order);
        order.setAmount(calculateOrderAmount(orderProducts));
        return orderRepository.save(order).getId();
    }

    @Transactional
    public OrderDTO getUserOrderDTO(Long orderId) throws NotFoundException {
        return this.toOrderDTO(orderId);
    }

    private Product toProduct(ProductDTO productDTO) {
        return productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductException("Cant find product by id:" + productDTO.getId()));
    }

    private BigDecimal calculateOrderAmount(List<OrderProduct> orderProducts) {
        return orderProducts.stream().map(orderProduct -> orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderProduct> toOrderProductList(List<ProductDTO> productDTOS, Order order) {
        return productDTOS.stream().map(productDTO -> this.toOrderProduct(productDTO, order))
                .collect(Collectors.toList());
    }

    private OrderProduct toOrderProduct(ProductDTO productDTO, Order order) {
        return orderProductRepository.save(new OrderProduct(toProduct(productDTO), order, productDTO.getQuantity()));
    }

    private List<OrderProduct> getOrderProducts(Long orderId) {
        return orderProductRepository.findAllByOrder_Id(orderId);
    }

    public OrderDTO toOrderDTO(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(""));

        return OrderDTO.builder()
                .id(order.getId())
                .amount(order.getAmount())
                .products(this.toProductDTOS(getOrderProducts(orderId)))
                .build();
    }

    private List<ProductDTO> toProductDTOS(List<OrderProduct> orderProducts) {
        return orderProducts.stream().map(orderHelper::toProductDTO).collect(Collectors.toList());

    }

}
