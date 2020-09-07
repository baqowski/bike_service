package app.core.entity.type;

/**
 * @author Karol Bąk
 */
public enum OrderStatus {

    CREATED_BY_CLIENT,
    PAYMENT_IN_PROGRESS,
    PAYMENT_SUCCESSFULLY,
    PREPARED_TO_SENT,
    SENT_TO_CLIENT,
    RECEIVED_FROM_CLIENT,
    FINISHED,
}
