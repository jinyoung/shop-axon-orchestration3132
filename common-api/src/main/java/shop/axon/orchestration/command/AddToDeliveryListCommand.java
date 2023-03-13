package shop.axon.orchestration.command;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class AddToDeliveryListCommand {

    private String id; // Please comment here if you want user to enter the id directly
    private String address;
    private String customerId;
    private Integer quantity;
    private Long orderId;
}
