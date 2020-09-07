package app.core.entity.projection;

import app.core.entity.DeliveryAddress;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {DeliveryAddress.class}
)
public interface DeliveryAddressProjection {

    String getStreet();

    String getHouseNumber();

    String getPostalCode();

    String getCity();

    String getCountry();

    /*DeliveryOrderProjection getDeliveryOrder();*/


}
