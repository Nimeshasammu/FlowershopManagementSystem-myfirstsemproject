package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.OrderDetailsDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModel {

    public boolean saveOrderDetails(OrderDetailsDto orderDetailsDto)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Order_Details(order_id, item_id, quantity, payment_id) VALUES (?,?,?,?)";

        return CrudUtil.execute(
                sql,
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getItem_id(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPayment_id()
        );
    }

    public boolean updateOrderDetails(OrderDetailsDto orderDetailsDto)
            throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Order_Details SET quantity=?, payment_id=? WHERE order_id=? AND item_id=?";

        return CrudUtil.execute(
                sql,
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getPayment_id(),
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getItem_id()
        );
    }

    public boolean deleteOrderDetails(int orderId, int itemId)
            throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Order_Details WHERE order_id=? AND item_id=?";

        return CrudUtil.execute(sql, orderId, itemId);
    }

    public List<OrderDetailsDto> getAllOrderDetails()
            throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Order_Details");
        List<OrderDetailsDto> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(new OrderDetailsDto(
                    resultSet.getInt("order_id"),
                    resultSet.getInt("item_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("payment_id")
            ));
        }
        return list;
    }
}
