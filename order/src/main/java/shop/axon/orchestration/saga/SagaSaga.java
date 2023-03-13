package shop.axon.orchestration.saga;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shop.axon.orchestration.command.*;
import shop.axon.orchestration.event.*;

@Component
@Saga
@ProcessingGroup("SagaSaga")
public class SagaSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private String processId;

    @StartSaga
    @SagaEventHandler(associationProperty = "#correlation-key")
    public void onOrderPlaced(OrderPlacedEvent event) {
        DecreaseInventoryCommand command = new DecreaseInventoryCommand();

        commandGateway
            .send(command)
            .exceptionally(ex -> {
                CancelCommand cancelCommand = new CancelCommand();
                //
                return commandGateway.send(cancelCommand);
            });
    }

    @SagaEventHandler(associationProperty = "#correlation-key")
    public void onInventoryDescreased(InventoryDescreasedEvent event) {
        AddToDeliveryListCommand command = new AddToDeliveryListCommand();

        commandGateway
            .send(command)
            .exceptionally(ex -> {
                IncreaseInventoryCommand increaseInventoryCommand = new IncreaseInventoryCommand();
                //
                return commandGateway.send(increaseInventoryCommand);
            });
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "#correlation-key")
    public void onDeliveryStarted(DeliveryStartedEvent event) {}
}
