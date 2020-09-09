package app.core.service;

import app.bike_app.AbstractIntegrationTest;
import app.core.entity.*;
import app.core.entity.dto.PaymentResponseDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.repository.*;
import app.core.entity.type.PaymentType;
import app.core.service.helper.OrderHelper;
import app.core.service.mapper.payu.PayuMapper;
import app.jwt.dto.PayUResponseAuthDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karol Bąk
 */
class PayUServiceTest extends AbstractIntegrationTest {

    @Autowired
    private PayUService payUService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private PayuMapper payuMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        initData();
    }

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Transactional
    @Test
    public void shouldCreateOrderWith2Products() {
        List<Order> orders = (List<Order>) orderRepository.findAll();

        Assertions.assertEquals(1, orders.size());
        Assertions.assertEquals(2, orders.get(0).getProducts().size());
    }

    @Transactional
    @Test
    public void shouldMapOrderProductToBuyNowDTO() {
        //given
        Order order = orderRepository.findAll().iterator().next();
        order.setAmount(orderHelper.calculateOrderSummaryPrice(order));

        //when
        PayuDTO payuDTO = payuMapper.map(order);

        //then
        Assertions.assertEquals(order.getProducts().size(), payuDTO.getProducts().size());
        Assertions.assertEquals(order.getProducts().get(0).getProduct().getName(), payuDTO.getProducts().get(0).getName());
        Assertions.assertEquals(order.getProducts().get(1).getProduct().getName(), payuDTO.getProducts().get(1).getName());
        Assertions.assertEquals(BigDecimal.valueOf(23), order.getAmount());
        Assertions.assertEquals(order.getAmount().toString(), payuDTO.getTotalAmount());

    }

    @Test
    public void shouldAuthorizeWithPayU() {
        PayUResponseAuthDTO payUResponseAuthDTO = payUService.authorizeWithPayU();
        Assertions.assertNotNull(payUResponseAuthDTO);
        Assertions.assertNotNull(payUResponseAuthDTO.getAccess_token());
        Assertions.assertNotNull(payUResponseAuthDTO.getExpires_in());
        Assertions.assertNotNull(payUResponseAuthDTO.getGrant_type());
        Assertions.assertNotNull(payUResponseAuthDTO.getToken_type());
    }


    @Transactional
    @Test
    public void shouldCreatePayuOrder() {
        Order order = orderRepository.findAll().iterator().next();
        PayuDTO payuDTO = payuMapper.map(order);
        PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);

        Assertions.assertNotNull(payuOrderResponseDTO);
        Assertions.assertNotNull(payuOrderResponseDTO.getOrderId());
        Assertions.assertNotNull(payuOrderResponseDTO.getRedirectUri());
    }

    @Transactional
    @Test
    public void shouldCreateNewPaymentPayu(){
        Long id = orderRepository.findAll().iterator().next().getId();
        Payment payment = orderService.createOrUpdateOrderPayment(id, PaymentType.PAYU );
        PaymentResponseDTO paymentResponseDTO = paymentService.createNewPaymentOrUpdateExisting(id, PaymentType.PAYU);
        Assertions.assertNotNull(paymentResponseDTO);
        Assertions.assertNotNull(paymentResponseDTO.getPaymentId());
        Assertions.assertNotNull(paymentResponseDTO.getRedirectUri());

    }

    public void initData() {
        Order order = createOrder();
        List<OrderProduct> orderProducts = Arrays.asList(
                createOrderProduct(createProduct("Product1", BigDecimal.TEN), order, 1),
                createOrderProduct(createProduct("Product2", BigDecimal.ONE), order, 3)
        );
        order.setProducts(orderProducts);
        order.setUser(createUser());
        order.setDeliveryOrder(createDeliveryOrder());
        /*orderRepository.save(order);*/

    }

    private Product createProduct(String name, BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }

    private Order createOrder() {
        Order order = new Order();
        return orderRepository.save(order);
    }

    private OrderProduct createOrderProduct(Product product, Order order, Integer quantity) {
        OrderProduct orderProduct = new OrderProduct(product, order, quantity);
        return orderProductRepository.save(orderProduct);
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("test@email.com");
        user.setPhone("123-456-789");
        return userRepository.save(user);
    }

    private DeliveryOrder createDeliveryOrder() {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setDelivery(createDelivery());
        deliveryOrder.setDeliveryAddress(createDeliveryAddress());
        return deliveryOrderRepository.save(deliveryOrder);
    }

    private Delivery createDelivery() {
        Delivery delivery = new Delivery();
        delivery.setCost(BigDecimal.TEN);
        delivery.setType("Kurier");
        return deliveryRepository.save(delivery);
    }

    private DeliveryAddress createDeliveryAddress() {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setStreet("test street");
        deliveryAddress.setHouseNumber("test number");
        deliveryAddress.setPostalCode("test postal code");
        deliveryAddress.setCity("test city");
        deliveryAddress.setCountry("test country");
        return deliveryAddressRepository.save(deliveryAddress);
    }

}
