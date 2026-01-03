package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.OrderDetailsDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModel {

    public boolean saveOrderDetails(OrderDetailsDto orderDetailsDto)
            throws SQLException, ClassNotFoundException {

        if (orderDetailsDto == null || orderDetailsDto.getQuantity() <= 0) {
            return false;
        }

        String sql = "INSERT INTO Order_Details (order_id, item_id, quantity, unit_price) VALUES (?,?,?,?)";

        return CrudUtil.execute(
                sql,
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getItem_id(),
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getUnit_price()
        );
    }


    public boolean updateOrderDetails(OrderDetailsDto orderDetailsDto)
            throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Order_Details SET quantity=?, unit_price=? WHERE order_id=? AND item_id=?";

        return CrudUtil.execute(
                sql,
                orderDetailsDto.getQuantity(),
                orderDetailsDto.getUnit_price(),
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
                    resultSet.getString("order_id"),
                    resultSet.getString("item_id"),
                    resultSet.getInt("quantity"),
                    resultSet.getInt("unit_price")
            ));
        }
        return list;
    }

    public double todaySale() throws SQLException, ClassNotFoundException {
        String sql = """
        SELECT SUM(od.quantity * od.unit_price) AS total_sales
        FROM Order_Details od
        JOIN Orders o ON od.order_id = o.order_id
        WHERE o.order_date = ?
    """;

        ResultSet rs = CrudUtil.execute(sql, java.sql.Date.valueOf(LocalDate.now()));

        if (rs.next()) {
            return rs.getDouble("total_sales");
        }
        return 0.0;
    }


}
