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
import lk.ijse.flowershop.dto.CustomerDto;
import lk.ijse.flowershop.dto.ItemDto;
import lk.ijse.flowershop.dto.tm.CartTM;
import lk.ijse.flowershop.model.CustomerModel;
import lk.ijse.flowershop.model.ItemModel;
import lk.ijse.flowershop.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    @FXML
    private AnchorPane AdminManageAnchorPane;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<CustomerDto> cmdCustomerId;

    @FXML
    private ChoiceBox<String> cmdPay;

    @FXML
    private ComboBox<ItemDto> cmdProductId;

    @FXML
    private TableView<CartTM> tableProduct;

    @FXML
    private TableColumn<CartTM, String> colProduct;

    @FXML
    private TableColumn<CartTM, Integer> colQty;

    @FXML
    private TableColumn<CartTM, Double> colPrice;


    @FXML
    private Label discountLabel;

    @FXML
    private Label itemsCountLabel;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblName;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label subtotalLabel;


    @FXML
    private TextField textQty;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField txtPaid;

    private static CustomerModel customerModel = new CustomerModel();
    private static ItemModel itemModel = new ItemModel();
    private static OrderModel orderModel = new OrderModel();
    private ObservableList<CartTM> cartList = FXCollections.observableArrayList();


    @FXML
    void onActionAddToCart(ActionEvent event) {

        if (cmdProductId.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a product.");
            return;
        }

        if (textQty.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter quantity.");
            return;
        }

        try {
            ItemDto item = cmdProductId.getValue();
            String productName = item.getItem_name();
            int qty = Integer.parseInt(textQty.getText());
            double unitPrice = item.getUnit_price();
            double totalPrice = qty * unitPrice;
            totalPrice = Math.round(totalPrice * 100.0) / 100.0;


            // check stock
            if (qty > item.getQuantity()) {
                showAlert(Alert.AlertType.ERROR, "Not enough stock available!");
                return;
            }

            // check if already added
            for (CartTM cart : cartList) {
                if (cart.getProduct().equals(productName)) {
                    cart.setQty(cart.getQty() + qty);
                    cart.setPrice(cart.getQty() * unitPrice);
                    tableProduct.refresh();
                    return;
                }
            }
            CartTM cartTM = new CartTM(
                    productName,
                    qty,
                    totalPrice
            );

            cartList.add(cartTM);
            tableProduct.setItems(cartList);
            clearFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid quantity!");
        }
    }


    @FXML
    void onActionClear(ActionEvent event) {

    }

    @FXML
    void onActionConfirm(ActionEvent event) {

    }

    @FXML
    void onKeyBalance(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomers();
        loadItems();
        cmdPay.setItems(FXCollections.observableArrayList("Cash", "Card", "Check"));
        cmdPay.getSelectionModel().select("Cash");
        setupTableColumns();
    }

    private void setupTableColumns() {
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadCustomers() {
        try {
            List<CustomerDto> customerList = customerModel.getAllCustomers();
            cmdCustomerId.setItems(FXCollections.observableArrayList(customerList));
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading customers: " + e.getMessage());
        }
    }
    private void loadItems() {
        try {
            List<ItemDto> itemList = itemModel.getAllItems();
            cmdProductId.setItems(FXCollections.observableArrayList(itemList));
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading customers: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        cmdProductId.getSelectionModel().clearSelection();
        lblProductName.setText("");
        lblPrice.setText("");
        lblQtyOnHand.setText("");
        textQty.setText("");
    }

    public void onActionCustomerId(ActionEvent actionEvent) {
        setGenerateId();
        CustomerDto selectedCustomer = cmdCustomerId.getValue();
        if (selectedCustomer != null) {
            lblName.setText(selectedCustomer.getCus_name());
        }
    }
    public void onActionProductDetails(ActionEvent actionEvent) {
        ItemDto selectedItem = cmdProductId.getValue();
        if (selectedItem != null) {
            lblProductName.setText(selectedItem.getItem_name());
            lblPrice.setText(String.valueOf(selectedItem.getUnit_price()));
            lblQtyOnHand.setText(String.valueOf(selectedItem.getQuantity()));

        }
    }
    private void setGenerateId() {
        try {
            String lastId = orderModel.getLastOrderId();
            int newId = 1;

            if (lastId != null && lastId.startsWith("O")) {
                newId = Integer.parseInt(lastId.substring(1)) + 1;
            }

            String formattedId = String.format("O%03d", newId);
            lblOrderId.setText(formattedId);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
