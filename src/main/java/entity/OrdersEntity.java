package entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;
    @Column (name = "oderDate")
    private LocalDate oderDate;

    @Column (name = "customerName")
    private String customerName;
    @Column (name = "customerAddress")
    private String customerAddress;

    @OneToMany (mappedBy = "orders", fetch = FetchType.EAGER)
    private List<OrderDetailsEntity> orderDetailsEntities;

    public OrdersEntity() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOderDate() {
        return oderDate;
    }

    public void setOderDate(LocalDate oderDate) {
        this.oderDate = oderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<OrderDetailsEntity> getOrderDetailsEntities() {
        return orderDetailsEntities;
    }

    public void setOrderDetailsEntities(List<OrderDetailsEntity> orderDetailsEntities) {
        this.orderDetailsEntities = orderDetailsEntities;
    }

    @Override
    public String toString() {
        return "OrdersEntity{" +
                "id=" + id +
                ", oderDate=" + oderDate +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", orderDetailsEntities=" + orderDetailsEntities +
                '}';
    }
}
