package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.PaymentDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public boolean paymentSave(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Payment(payment_id, payment_method, payment_date, total_amount, status, order_id) " +
                "VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                paymentDto.getPayment_id(),
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

    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Payment");
        List<PaymentDto> paymentDtoList = new ArrayList<>();

        while (resultSet.next()) {
            paymentDtoList.add(
                    new PaymentDto(
                            resultSet.getString("payment_id"),
                            resultSet.getString("payment_method"),
                            resultSet.getTimestamp("payment_date").toLocalDateTime(),
                            resultSet.getDouble("total_amount"),
                            resultSet.getString("status"),
                            resultSet.getString("order_id")
                    )
            );
        }
        return paymentDtoList;
    }
    public String getLastPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(payment_id) FROM Payment");
        if (resultSet.next()) {
            return resultSet.getString("payment_id");
        }
        return null;
    }
}
