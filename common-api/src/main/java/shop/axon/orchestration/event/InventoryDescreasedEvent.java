package shop.axon.orchestration.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InventoryDescreasedEvent {

    private String id;
    private Long orderId;
    private Integer qty;
}
