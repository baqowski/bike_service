package app.core.service;

import app.core.entity.*;
import app.core.entity.dto.ProductDTO;
import app.core.entity.type.OrderStatus;
import app.core.exception.ProductException;
import app.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserProductRepository userProductRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public void create(List<ProductDTO> productDTOS) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user"));

        List<UserProduct> userProducts = mapToProductList(productDTOS);

        Order order = new Order();
        order.setAmount(calculateOrderAmount(userProducts));
        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setUser(user);
        orderRepository.save(order);

        mapUserProductsToOrderProducts(userProducts, order);


    }

    private List<UserProduct> mapToProductList(List<ProductDTO> productDTOList) {
        return productDTOList.stream().map(this::toUserProduct).collect(Collectors.toList());
    }

    private UserProduct toUserProduct(ProductDTO productDTO) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user"));

        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductException("Cant find product by id:" + productDTO.getId()));

        return userProductRepository.save(new UserProduct(user, product, productDTO.getCount()));
    }

    /*private List<Product> setUserProducts(List<ProductDTO> productDTOS) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user"));

        user.setProducts(mapToProductList(productDTOS));
        return userRepository.save(user).getProducts();
    }*/

    private BigDecimal calculateOrderAmount(List<UserProduct> userProducts) {
        return userProducts.stream().map(userProduct -> userProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(userProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderProduct> mapUserProductsToOrderProducts(List<UserProduct> userProducts, Order order) {
       return userProducts.stream().map(userProduct -> this.toOrderProduct(userProduct, order))
               .collect(Collectors.toList());
    }

    private OrderProduct toOrderProduct(UserProduct userProduct, Order order) {
        return orderProductRepository.save(new OrderProduct(userProduct, order));
    }
}
