package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.PaymentDto;
import lk.ijse.flowershop.dto.tm.PaymentTM;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public boolean paymentSave(PaymentDto paymentDto)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Payment(payment_method, payment_date, total_amount, status, order_id) " +
                "VALUES (?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                paymentDto.getPayment_method(),
                paymentDto.getPayment_date(),
                paymentDto.getTotal_amount(),
                paymentDto.getStatus(),
                paymentDto.getOrder_id()
        );
    }


    public boolean paymentUpdate(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Payment SET payment_method=?, payment_date=?, total_amount=?, status=?, order_id=? " +
                "WHERE payment_id=?";
        return CrudUtil.execute(
                sql,
                paymentDto.getPayment_method(),
                paymentDto.getPayment_date(),
                paymentDto.getTotal_amount(),
                paymentDto.getStatus(),
                paymentDto.getOrder_id(),
                paymentDto.getPayment_id()
        );
    }

    public boolean paymentDelete(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Payment WHERE payment_id=?";
        return CrudUtil.execute(sql, paymentDto.getPayment_id());
    }


    public String getLastPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(payment_id) FROM Payment");
        if (resultSet.next()) {
            return resultSet.getString("payment_id");
        }
        return null;
    }
    public List<PaymentTM> getAllPayments() throws SQLException, ClassNotFoundException {

        String sql = """
            SELECT p.payment_id,
                   p.order_id,
                   p.payment_method,
                   p.total_amount,
                   p.payment_date,
                   o.cus_id,
                   p.status
            FROM Payment p
            JOIN Orders o ON p.order_id = o.order_id
        """;

        ResultSet rst = CrudUtil.execute(sql);

        List<PaymentTM> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new PaymentTM(
                    String.valueOf(rst.getInt("payment_id")),
                    rst.getString("order_id"),
                    rst.getString("payment_method"),
                    rst.getDouble("total_amount"),
                    rst.getTimestamp("payment_date").toLocalDateTime(),
                    rst.getString("cus_id"),
                    rst.getString("status")
            ));
        }
        return list;
    }
}
