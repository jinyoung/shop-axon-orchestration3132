package shop.axon.orchestration.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDeliveryStartedEvent {

    private Long id;
    private String productId;
    private Integer qty;
    private String customerId;
    private Double amount;
    private String status;
    private String address;
}
