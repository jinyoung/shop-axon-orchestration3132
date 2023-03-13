package shop.axon.orchestration.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeliveryReturnedEvent {

    private String id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;
}
