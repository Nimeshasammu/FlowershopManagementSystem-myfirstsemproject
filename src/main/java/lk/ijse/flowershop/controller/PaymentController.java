package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.flowershop.dto.tm.PaymentTM;
import lk.ijse.flowershop.model.PaymentModel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colOrderId;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentTM, Double> colTotalAmount;

    @FXML
    private TableColumn<PaymentTM, LocalDateTime> colPaymentDate;

    @FXML
    private TableColumn<PaymentTM, String> colCustomerId;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    private final PaymentModel paymentModel = new PaymentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadPaymentTable();
    }

    private void setCellValueFactory() {

        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        colPaymentDate.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
    }


    private void loadPaymentTable() {
        try {
            ObservableList<PaymentTM> obList =
                    FXCollections.observableArrayList(paymentModel.getAllPayments());
            tblPayment.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
