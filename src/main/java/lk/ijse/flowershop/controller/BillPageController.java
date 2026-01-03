package lk.ijse.flowershop.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.flowershop.dto.OrderDto;
import lk.ijse.flowershop.dto.PaymentDto;
import lk.ijse.flowershop.model.CustomerModel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class BillPageController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label discountLabel;

    @FXML
    private Label itemsCountLabel;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label txtCustomerName;

    @FXML
    private Label txtInvoiceNo;

    @FXML
    private Label txtOrderId;

    @FXML
    private Label txtPaymentId;

    @FXML
    private Label txtPaymentMethod;

    private static CustomerModel customerModel = new CustomerModel();


    @Getter
    @Setter
    private boolean isSave = false;


    @FXML
    void btnCancelOnAction(ActionEvent event) {
        setSave(false);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {
// print
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        setSave(true);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    void setlabel(OrderDto orderDto, PaymentDto paymentDto,int itemsCount) throws SQLException, ClassNotFoundException {
        String orderId = orderDto.getOrder_id(); // PAY001
        String invoiceNo = "IN" + orderId.substring(1);  // remove O, add IN
        txtInvoiceNo.setText(invoiceNo);                   // IN001

        txtOrderId.setText(String.valueOf(orderDto.getOrder_id()));
        txtCustomerName.setText(customerModel.getCustomerNameById(orderDto.getCus_id()));
        String paymentId =  "PAY" + orderId.substring(1);
        txtPaymentId.setText(paymentId);
        txtPaymentMethod.setText(paymentDto.getPayment_method());
//        discountLabel.setText(String.valueOf(ordersPaymentDto.getDiscount()));
        itemsCountLabel.setText(String.valueOf(itemsCount));
        subtotalLabel.setText(String.valueOf(orderDto.getTotal_amount()));
        totalLabel.setText(String.valueOf(orderDto.getTotal_amount()));
        datePicker.setValue(orderDto.getOrder_date());


    }
}

