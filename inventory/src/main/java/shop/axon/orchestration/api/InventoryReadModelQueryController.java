package shop.axon.orchestration.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import shop.axon.orchestration.query.*;

@RestController
public class InventoryReadModelQueryController {

    private final QueryGateway queryGateway;

    private final ReactorQueryGateway reactorQueryGateway;

    public InventoryReadModelQueryController(
        QueryGateway queryGateway,
        ReactorQueryGateway reactorQueryGateway
    ) {
        this.queryGateway = queryGateway;
        this.reactorQueryGateway = reactorQueryGateway;
    }

    @GetMapping("/inventoryReadModels")
    public CompletableFuture findAll(InventoryReadModelQuery query) {
        return queryGateway
            .query(
                query,
                ResponseTypes.multipleInstancesOf(InventoryReadModel.class)
            )
            .thenApply(resources -> {
                List modelList = new ArrayList<EntityModel<InventoryReadModel>>();

                resources
                    .stream()
                    .forEach(resource -> {
                        modelList.add(hateoas(resource));
                    });

                CollectionModel<InventoryReadModel> model = CollectionModel.of(
                    modelList
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/inventoryReadModels/{id}")
    public CompletableFuture findById(@PathVariable("id") Long id) {
        InventoryReadModelSingleQuery query = new InventoryReadModelSingleQuery();
        query.setId(id);

        return queryGateway
            .query(
                query,
                ResponseTypes.optionalInstanceOf(InventoryReadModel.class)
            )
            .thenApply(resource -> {
                if (!resource.isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                return new ResponseEntity<>(
                    hateoas(resource.get()),
                    HttpStatus.OK
                );
            })
            .exceptionally(ex -> {
                throw new RuntimeException(ex);
            });
    }

    EntityModel<InventoryReadModel> hateoas(InventoryReadModel resource) {
        EntityModel<InventoryReadModel> model = EntityModel.of(resource);

        model.add(
            Link.of("/inventoryReadModels/" + resource.getId()).withSelfRel()
        );

        model.add(
            Link
                .of("/inventoryReadModels/" + resource.getId() + "/events")
                .withRel("events")
        );

        return model;
    }

    //<<< Etc / RSocket
    @MessageMapping("inventoryReadModels.all")
    public Flux<InventoryReadModel> subscribeAll() {
        return reactorQueryGateway.subscriptionQueryMany(
            new InventoryReadModelQuery(),
            InventoryReadModel.class
        );
    }

    @MessageMapping("inventoryReadModels.{id}.get")
    public Flux<InventoryReadModel> subscribeSingle(
        @DestinationVariable Long id
    ) {
        InventoryReadModelSingleQuery query = new InventoryReadModelSingleQuery();
        query.setId(id);

        return reactorQueryGateway.subscriptionQuery(
            query,
            InventoryReadModel.class
        );
    }
    //>>> Etc / RSocket

}
