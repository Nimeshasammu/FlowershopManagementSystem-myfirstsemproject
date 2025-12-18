package lk.ijse.flowershop.model;

import lk.ijse.flowershop.db.DBConnection;
import lk.ijse.flowershop.dto.OrderDetailsDto;
import lk.ijse.flowershop.dto.OrderDto;
import lk.ijse.flowershop.dto.PaymentDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    private final OrderModel orderModel = new OrderModel();
    private final ItemModel itemModel = new ItemModel();
    private final PaymentModel paymentModel = new PaymentModel();


    public boolean saveOrder(OrderDto ordersDto)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Orders(order_date, time, total_amount, cus_id, user_id) VALUES (?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                ordersDto.getOrder_date(),
                ordersDto.getTime(),
                ordersDto.getTotal_amount(),
                ordersDto.getCus_id(),
                ordersDto.getUser_id()
        );
    }

    public boolean updateOrder(OrderDto ordersDto)
            throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Orders SET order_date=?, time=?, total_amount=?, cus_id=?, user_id=? WHERE order_id=?";

        return CrudUtil.execute(
                sql,
                ordersDto.getOrder_date(),
                ordersDto.getTime(),
                ordersDto.getTotal_amount(),
                ordersDto.getCus_id(),
                ordersDto.getUser_id(),
                ordersDto.getOrder_id()
        );
    }

    public boolean deleteOrder(int orderId)
            throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Orders WHERE order_id=?";
        return CrudUtil.execute(sql, orderId);
    }

    public List<OrderDto> getAllOrders()
            throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders");
        List<OrderDto> orderList = new ArrayList<>();

        while (resultSet.next()) {
            orderList.add(new OrderDto(
                    resultSet.getInt("order_id"),
                    resultSet.getDate("order_date").toLocalDate(),
                    resultSet.getTime("time") != null
                            ? resultSet.getTime("time").toLocalTime()
                            : null,
                    resultSet.getDouble("total_amount"),
                    resultSet.getInt("cus_id"),
                    resultSet.getInt("user_id"),
                    null
            ));
        }
        return orderList;
    }
    public boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isOrderSaved = orderModel.saveOrder(orderDto);
            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }
            for (OrderDetailsDto detail : orderDto.getCartList()) {
                boolean isOrderDetailsSaved = orderDetailsModel.saveOrderDetails(detail);
                if (!isOrderDetailsSaved) {
                    connection.rollback();
                    return false;
                }
                boolean isItemUpdated = itemModel.stockUpdate(detail.getItem_id(),detail.getQuantity());
                if (!isItemUpdated) {
                    connection.rollback();
                    return false;
                }
            }
            PaymentDto paymentDto = new PaymentDto(
                    1,
                    "Cash",
                    orderDto.getOrder_date().atStartOfDay(),
                    orderDto.getTotal_amount(),
                    "success",
                    orderDto.getOrder_id()
            );

            boolean isPaymentSave = paymentModel.paymentSave(paymentDto);
            if (!isPaymentSave) {
                connection.rollback();
                return false;
            }
            connection.commit();
            return  true;
        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
