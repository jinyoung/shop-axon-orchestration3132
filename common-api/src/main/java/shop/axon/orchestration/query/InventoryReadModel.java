package shop.axon.orchestration.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "Inventory_table")
@Data
@Relation(collectionRelation = "inventories")
public class InventoryReadModel {

    @Id
    private String id;

    private Long orderId;

    private Integer qty;
}
