package lk.ijse.flowershop.model;

import lk.ijse.flowershop.db.DBConnection;
import lk.ijse.flowershop.dto.EmployeeDto;
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
    private final ItemModel itemModel = new ItemModel();
    private final PaymentModel paymentModel = new PaymentModel();


    public boolean orderSave(OrderDto orderDto) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Orders(order_id,order_date,time,total_amount,cus_id,user_id) VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                orderDto.getOrder_id(),
                orderDto.getOrder_date(),
                orderDto.getTime(),
                orderDto.getTotal_amount(),
                orderDto.getCus_id(),
                orderDto.getUser_id()
        );
    }

    // ================= PLACE ORDER =================
    public boolean placeOrder(OrderDto orderDto)
            throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderSave(orderDto);
            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            for (OrderDetailsDto detail : orderDto.getCartList()) {

                // ✅ IMPORTANT: set order_id
                detail.setOrder_id(orderDto.getOrder_id());

                boolean isDetailSaved = orderDetailsModel.saveOrderDetails(detail);
                if (!isDetailSaved) {
                    connection.rollback();
                    return false;
                }

                boolean isStockUpdated =
                        itemModel.stockUpdate(detail.getItem_id(), detail.getQuantity());

                if (!isStockUpdated) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }
    }


    // ================= GET ALL ORDERS =================
  /*  public List<OrderDto> getAllOrders()
            throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Orders");
        List<OrderDto> orderList = new ArrayList<>();

        while (resultSet.next()) {
            orderList.add(new OrderDto(
                    resultSet.getString("order_id"),
                    resultSet.getDate("order_date").toLocalDate(),
                    resultSet.getTime("time") != null
                            ? resultSet.getTime("time").toLocalTime()
                            : null,
                    resultSet.getDouble("total_amount"),
                    resultSet.getString("cus_id"),
                    resultSet.getString("user_id"),
                    null
            ));
        }
        return orderList;
    }*/

    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(order_id) FROM Orders");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
