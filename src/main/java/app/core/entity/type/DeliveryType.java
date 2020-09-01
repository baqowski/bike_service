package app.core.entity.type;

/**
 * @author Karol Bąk
 */
public enum DeliveryType {

    COURIER_BOX("Przyesyłka kurierska"),
    PERSONAL_COLLECTION("Odbiór osobisty"),
    REGISTERED_LETTER("List polecony"),
    PAYMENT_ON_DELIVER("Paczka pobraniowa"),
    LETTER_PRIORITY("Paczka pocztoa priorytetowa"),
    IN_POST("In post");

    DeliveryType(String s) {
    }
}
