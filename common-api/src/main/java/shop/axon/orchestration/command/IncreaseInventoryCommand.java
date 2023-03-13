package shop.axon.orchestration.command;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class IncreaseInventoryCommand {

    private String id; // Please comment here if you want user to enter the id directly
    private Long orderId;
    private Integer qty;
}
