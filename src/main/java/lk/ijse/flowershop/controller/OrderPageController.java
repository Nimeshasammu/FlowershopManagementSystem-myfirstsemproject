package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.flowershop.dto.tm.ProductTM;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class OrderPageController implements Initializable {

    @FXML
    private AnchorPane AdminManageAnchorPane;

    @FXML
    private Button btnClear, btnComfirm;

    @FXML
    private TableColumn<ProductTM, String> colProduct;

    @FXML
    private TableColumn<ProductTM, Integer> colQty;

    @FXML
    private TableColumn<ProductTM, Double> colPrice;

    @FXML
    private TableView<ProductTM> tableProduct;

    @FXML
    private Label discountLabel, itemsCountLabel, lblBalance, subtotalLabel, totalLabel;

    @FXML
    private TextField textCustomerId, textOrderId, textProductId,
            textProductName, textPrice, textQty, txtPaid;

    @FXML
    private TextField textName;

    @FXML
    private ChoiceBox<String> textPay;

    private final ObservableList<ProductTM> cartList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        initPayMethod();
        resetPage();
    }

    private void setupTable() {
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableProduct.setItems(cartList);
    }

    private void initPayMethod() {
        textPay.setItems(FXCollections.observableArrayList("Cash", "Card"));
        textPay.getSelectionModel().selectFirst();
    }

    // ---------------- ADD TO CART ----------------
    @FXML
    void onActionAddToCart(ActionEvent event) {

        try {
            String productName = textProductName.getText();
            int qty = Integer.parseInt(textQty.getText());
            double price = Double.parseDouble(textPrice.getText());

            if (productName.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Select a product!");
                return;
            }

            // If product already exists → update qty
            for (ProductTM tm : cartList) {
                if (tm.getProduct().equals(productName)) {
                    tm.setQty(tm.getQty() + qty);
                    tableProduct.refresh();
                    updateSummary();
                    clearProductFields();
                    return;
                }
            }

            cartList.add(new ProductTM(productName, qty, price));
            updateSummary();
            clearProductFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid quantity or price!");
        }
    }

    // ---------------- SUMMARY ----------------
    private void updateSummary() {
        double subtotal = 0;
        Set<String> uniqueItems = new HashSet<>();

        for (ProductTM tm : cartList) {
            subtotal += tm.getQty() * tm.getPrice();
            uniqueItems.add(tm.getProduct());
        }

        itemsCountLabel.setText(String.valueOf(uniqueItems.size()));
        subtotalLabel.setText(String.format("%.2f", subtotal));
        discountLabel.setText("0.00");
        totalLabel.setText(String.format("%.2f", subtotal));
    }

    // ---------------- BALANCE ----------------
    @FXML
    void onKeyBalance(KeyEvent event) {
        try {
            double paid = txtPaid.getText().isEmpty() ? 0 : Double.parseDouble(txtPaid.getText());
            double total = Double.parseDouble(totalLabel.getText());
            lblBalance.setText(String.format("%.2f", paid - total));
        } catch (NumberFormatException e) {
            lblBalance.setText("0.00");
        }
    }

    // ---------------- CLEAR ----------------
    @FXML
    void onActionClear(ActionEvent event) {
        resetPage();
    }

    private void resetPage() {
        cartList.clear();
        tableProduct.refresh();

        itemsCountLabel.setText("0");
        subtotalLabel.setText("0.00");
        discountLabel.setText("0.00");
        totalLabel.setText("0.00");
        lblBalance.setText("");

        txtPaid.clear();
        clearProductFields();
    }

    private void clearProductFields() {
        textProductId.clear();
        textProductName.clear();
        textPrice.clear();
        textQty.clear();
    }

    // ---------------- CONFIRM ----------------
    @FXML
    void onActionComfirm(ActionEvent event) {

        if (cartList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart is empty!");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Order Confirmed Successfully!");
        resetPage();
        textCustomerId.setText("");
        textName.setText("");
        textOrderId.setText("");
    }

    // ---------------- ALERT ----------------
    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK).show();
    }
}
