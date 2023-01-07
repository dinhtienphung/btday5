import config.SpringConfig;
import entity.OrderDetailsEntity;
import entity.OrdersEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.OrderDetailRepo;
import repository.OrdersRepo;

import java.nio.file.OpenOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    static OrdersRepo ordersRepo = context.getBean("ordersRepo", OrdersRepo.class);
    static OrderDetailRepo orderDetailRepo = context.getBean("orderDetailRepo", OrderDetailRepo.class);
//    static OrdersRepo ordersRepo = context.getBean("ordersRepo", OrdersRepo.class);
//    static OrderDetailRepo orderDetailRepo = context.getBean("orderDetailRepo", OrderDetailRepo.class);


    public static void main(String[] args) {
        int n;
        boolean check = false;
        do {
            System.out.println("1. Tạo mới order lưu vào database.");
            System.out.println("2. Tạo mới orderDetail.");
            System.out.println("3. List All 2 bảng Order và Detail.");
            System.out.println("4. Tìm kiếm id Của Order.");
            System.out.println("5. List Đơn hàng trong tháng.");
            System.out.println("6. List Đơn hàng lớn hơn 3000.");
            System.out.println("7. List Đơn hàng mua theo tên.");
            System.out.println("8. Thoát.");
            System.out.print("nhập số : ");
            Scanner sf = new Scanner(System.in);
            switch (n = sf.nextInt()) {
                case 1:
                    createOrders();
                    break;
                case 2:
                    creatOrderDetails();
                    break;
                case 3:
                    ListOderAndDetails();
                    break;
                case 4:
                    getIdOrderandDetail();
                    break;
                case 5:
                    currentMonth();
                    break;
                case 6:
                    listAllOrderHaveTotalAmountMoreThan1000();
                    break;
                case 7:
                    listAllOrderBuyProductByName();
                    break;
                case 8:
                    check = true;
                    break;
            }
        } while (!check);
    }

    public static void  ListOderAndDetails() {
        List<OrdersEntity> or1 = (List<OrdersEntity>) ordersRepo.findAll();
        for (OrdersEntity ordersEntity : or1) {
            List<OrderDetailsEntity> od1 = (List<OrderDetailsEntity>) orderDetailRepo.findAll();
            for (OrderDetailsEntity orderDetailsEntity : od1) {
                System.out.println("tim all order" + od1.toString());
            }
            System.out.println("tim all orderDetails" + or1.toString());
        }
    }

    public static void getIdOrderandDetail() {
        Scanner sc = new Scanner(System.in);
        System.out.print("* Số ID Tìm Kiếm: ");
        int orderId = sc.nextInt();
        Optional<OrdersEntity> orderById = ordersRepo.findById(orderId);
        System.out.println("ID là: " + orderById.toString());
        List<OrderDetailsEntity> orderDetailByOrderID = orderDetailRepo.getOrderDetailByOrderId(orderId);
        for (OrderDetailsEntity orderDetails : orderDetailByOrderID) {
            System.out.println(orderDetails.toString());
        }
    }

    public static void currentMonth() {
        System.out.println(" List Đơn hàng trong tháng");
        List<OrdersEntity> listOrderInCurrentMonth = ordersRepo.getOrderByCurMonth();
        for (OrdersEntity order: listOrderInCurrentMonth) {
            System.out.println(order.toString());
        }
    }

    public static void listAllOrderHaveTotalAmountMoreThan1000() {
        System.out.print("List Đơn hàng lớn hơn 3000");
        List<OrdersEntity> listOrderMoreThan1000 = ordersRepo.getOrderBySumPrice();
        for (OrdersEntity order: listOrderMoreThan1000) {
            System.out.println(order.toString());
        }
    }

public static void listAllOrderBuyProductByName() {
    Scanner sc = new Scanner(System.in);
    System.out.print("List Đơn hang mua theo tên: ");
    String nameOfProduct = sc.nextLine();
    List<OrdersEntity> listOrderByName = ordersRepo.getOrderByProductName(nameOfProduct);
    for (OrdersEntity order: listOrderByName) {
        System.out.println(order.toString());
    }
}

    public static void createOrders () {
        OrdersEntity ordersEntity = insertNewOrder();
        ordersRepo.save(ordersEntity);
    }

    public static OrdersEntity insertNewOrder() {
        Scanner sc = new Scanner(System.in);
        OrdersEntity ordersEntity = new OrdersEntity();
        System.out.println("customerName");
        ordersEntity.setCustomerName(sc.nextLine());
        System.out.println("customerAddress");
        ordersEntity.setCustomerAddress(sc.nextLine());
        ordersEntity.setOderDate(LocalDate.now());
        return ordersEntity;
        }

    public static OrderDetailsEntity insertNewOrderDetails() {
        Scanner sc = new Scanner(System.in);
        OrderDetailsEntity orderDetailsEntity = new OrderDetailsEntity();
        System.out.println("ProductName");
        orderDetailsEntity.setProductName(sc.nextLine());
        System.out.println("Quantity");
        orderDetailsEntity.setQuantity(sc.nextInt());
        System.out.println("Unitprice");
        orderDetailsEntity.setUnitPrice(sc.nextDouble());
        return orderDetailsEntity;
    }


    public static  void creatOrderDetails () {
        Scanner sc = new Scanner(System.in);
        OrderDetailsEntity orderDetailsEntity = insertNewOrderDetails ();
        System.out.println("insert orderID");
//        OrdersEntity orders = ordersRepo.findById(sc.nextInt()).get();
        Optional<OrdersEntity> ordersEntityOpenOption = ordersRepo.findById(sc.nextInt());
        orderDetailsEntity.setOrders(ordersEntityOpenOption.get());
        orderDetailRepo.save(orderDetailsEntity);

    }




}
