package repository;

import entity.OrdersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends CrudRepository<OrdersEntity, Integer> {

    @Query(value = "select * from orders where month(orderDate) = month(CURDATE())", nativeQuery = true)
    List<OrdersEntity> getOrderByCurMonth();

    @Query(value = "select * from orders  o\n" +
            "inner join orderdetails od\n" +
            "on o.id = od.orderId\n" +
            "where od.productName = ?1", nativeQuery = true)
    List<OrdersEntity> getOrderByProductName(String productName);

    @Query(value = "select * from orders  o\n" +
            "inner join orderdetails as od\n" +
            "on o.id = od.orderId\n" +
            "group by o.id\n" +
            "having SUM(od.unitPrice * od.quantity) > 3000", nativeQuery = true)
    List<OrdersEntity> getOrderBySumPrice();


}
