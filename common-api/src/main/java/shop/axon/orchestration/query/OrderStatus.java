package shop.axon.orchestration.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "OrderStatus_table")
@Data
@Relation(collectionRelation = "orderStatuses")
public class OrderStatus {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String status;
    private Double amount;
    private Long qty;
    private String productId;
}
