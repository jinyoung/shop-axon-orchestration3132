package shop.axon.orchestration.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import java.math.BigDecimal;
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
public class OrderAggregate {

    @AggregateIdentifier
    private String id;

    private String productId;
    private Integer qty;
    private String customerId;
    private BigDecimal amount;
    private String status;
    private String address;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(OrderCommand command) {
        OrderPlacedEvent event = new OrderPlacedEvent();
        BeanUtils.copyProperties(command, event);

        //TODO: check key generation is properly done
        if (event.getId() == null) event.setId(createUUID());

        apply(event);
    }

    @CommandHandler
    public void handle(CancelCommand command) {
        OrderCancelledEvent event = new OrderCancelledEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    @CommandHandler
    public void handle(UpdateStatusCommand command) {
        OrderDeliveryStartedEvent event = new OrderDeliveryStartedEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    //<<< Etc / ID Generation
    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    //>>> Etc / ID Generation

    //<<< EDA / Event Sourcing

    @EventSourcingHandler
    public void on(OrderPlacedEvent event) {
        BeanUtils.copyProperties(event, this);
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(OrderDeliveryStartedEvent event) {
        //TODO: business logic here

    }
    //>>> EDA / Event Sourcing

}
