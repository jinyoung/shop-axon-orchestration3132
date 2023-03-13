package shop.axon.orchestration.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import shop.axon.orchestration.command.*;
import shop.axon.orchestration.event.*;
import shop.axon.orchestration.query.*;

@Aggregate
@Data
@ToString
public class InventoryAggregate {

    @AggregateIdentifier
    private String id;

    private Long orderId;
    private Integer qty;

    public InventoryAggregate() {}

    @CommandHandler
    public InventoryAggregate(DecreaseInventoryCommand command) {}

    @CommandHandler
    public InventoryAggregate(IncreaseInventoryCommand command) {}

    //<<< Etc / ID Generation
    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    //>>> Etc / ID Generation

    //<<< EDA / Event Sourcing

    @EventSourcingHandler
    public void on(InventoryDescreasedEvent event) {
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(InventoryIncreasedEvent event) {
        //TODO: business logic here

    }
    //>>> EDA / Event Sourcing

}
