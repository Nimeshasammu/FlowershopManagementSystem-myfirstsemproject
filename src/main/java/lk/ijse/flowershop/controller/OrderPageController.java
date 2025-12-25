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
import javafx.stage.StageStyle;
import lk.ijse.flowershop.dto.*;
import lk.ijse.flowershop.dto.tm.CartTM;
import lk.ijse.flowershop.model.CustomerModel;
import lk.ijse.flowershop.model.ItemModel;
import lk.ijse.flowershop.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    private ObservableList<CartTM> productList = FXCollections.observableArrayList();


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
            String productId = item.getItem_id();
            int qty = Integer.parseInt(textQty.getText());
            double unitPrice = item.getUnit_price();
            double totalPrice = qty * unitPrice;
            totalPrice = Math.round(totalPrice * 100.0) / 100.0;


            // check stock
            if (qty > item.getQuantity()) {
                showAlert(Alert.AlertType.ERROR, "Not enough stock available!");
                return;
            }

            for (CartTM cart : productList) {
                if (cart.getProduct().equals(productId)) {
                    cart.setQty(cart.getQty() + qty);
                    cart.setPrice(cart.getQty() * unitPrice);
                    tableProduct.refresh();
                    return;
                }
            }
            CartTM cartTM = new CartTM(
                    productId,
                    qty,
                    totalPrice
            );

            productList.add(cartTM);
            tableProduct.setItems(productList);
            updateSummary();
            clearProductFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid quantity!");
        }
    }


    @FXML
    void onActionClear(ActionEvent event) {
        resetPage();

    }
    private void updateSummary() {
        double subtotal = 0;
        Set<String> uniqueItems = new HashSet<>();

        for (CartTM tm : productList) {
            subtotal += tm.getPrice();
            uniqueItems.add(tm.getProduct());
        }

        itemsCountLabel.setText(String.valueOf(uniqueItems.size()));
        subtotalLabel.setText(String.format("%.2f", subtotal));
        discountLabel.setText("0.00");
        totalLabel.setText(String.format("%.2f", subtotal));
    }

    @FXML
    void onActionConfirm(ActionEvent event) {
        if (productList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart is empty!");
            return;
        }
        if (lblName.getText() == null || lblName.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a customer.");
            return;
        }
        try {
            String orderId = lblOrderId.getText();
            LocalDate orderDate = LocalDate.now();
            LocalTime orderTime = LocalTime.now();

            double totalPrice = Double.parseDouble(
                    totalLabel.getText().replace("Rs.", "").replace(",", "").trim()
            );

            String customerId = cmdCustomerId.getValue().getCus_id();
            String userId = Session.getCurrentUser().getUser_id();

            ArrayList<OrderDetailsDto> cartList = new ArrayList<>();
            for (CartTM item : productList) {
                cartList.add(new OrderDetailsDto(orderId, item.getProduct(), item.getQty(), item.getPrice()));
            }
            OrderDto orderDto = new OrderDto(orderId,orderDate,orderTime,totalPrice,customerId,userId,cartList);
            boolean isPlaced = orderModel.placeOrder(orderDto);
// ----
            if (isPlaced) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully!");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.getDialogPane().setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
                alert.show();
                resetPage();
                cmdCustomerId.getSelectionModel().clearSelection();
                lblName.setText("");
                setGenerateId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Order payment failed.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error placing order.");
        }
    }

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
    private void resetPage() {
        productList.clear();
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
        cmdProductId.getSelectionModel().clearSelection();
        lblProductName.setText("");
        lblPrice.setText("");
        lblQtyOnHand.setText("");
        textQty.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPage();
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
