package shop.axon.orchestration.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeliveryStartedEvent {

    private Long id;
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;
}
