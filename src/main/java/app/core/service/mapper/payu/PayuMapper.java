package app.core.service.mapper.payu;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.User;
import app.core.entity.dto.ClientDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuProductDTO;
import app.core.service.helper.OrderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@RequiredArgsConstructor
@Service
public class PayuMapper implements PayuDtoMapper {

    @Value("${payu.sandbox.clientId}")
    private String clientId;

    private final OrderHelper orderHelper;


    @Override
    public PayuDTO map(Order order) {
        PayuDTO payuDTO = new PayuDTO();

        ClientDTO clientDTO = this.map(order.getUser());
        List<PayuProductDTO> productDTOList = this.map(order.getProducts());

        payuDTO.setBuyer(clientDTO);
        payuDTO.setProducts(productDTOList);
        payuDTO.setMerchantPosId(clientId);
        payuDTO.setTotalAmount(mapTotalCostToPayuFormat(orderHelper.calculateOrderSummaryPrice(order)));
        payuDTO.setDescription("Bike service system");
        payuDTO.setCurrencyCode("PLN");
        payuDTO.setCustomerIp("127.0.0.1");
        payuDTO.setNotifyUrl("https://your.eshop.com/notify");

        return payuDTO;
    }

    private ClientDTO map(User user) {
        return ClientDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    private List<PayuProductDTO> map(List<OrderProduct> orderProducts) {
        return orderProducts.stream().map(this::toPayuProductDTO).collect(Collectors.toList());
    }

    private PayuProductDTO toPayuProductDTO(OrderProduct orderProduct) {
        return PayuProductDTO.builder()
                .name(orderProduct.getProduct().getName())
                .quantity(orderProduct.getQuantity().toString())
                .unitPrice(mapTotalCostToPayuFormat(orderProduct.getProduct().getPrice()))
                .build();
    }

    private String mapTotalCostToPayuFormat(BigDecimal bigDecimal) {
        return bigDecimal.toString().replace(".", "");
    }
}
