package shop.axon.orchestration.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InventoryIncreasedEvent {

    private Long id;
    private Long orderId;
    private Integer qty;
}
