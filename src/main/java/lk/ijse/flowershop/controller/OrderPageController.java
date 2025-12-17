package lk.ijse.flowershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class OrderPageController {

    @FXML
    private AnchorPane AdminManageAnchorPane;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnComfirm;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProduct;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private Label discountLabel;

    @FXML
    private Label itemsCountLabel;

    @FXML
    private Label lblBalance;

    @FXML
    private Label subtotalLabel;

    @FXML
    private TableView<?> tableProduct;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField txtPaid;

    @FXML
    void onActionClear(ActionEvent event) {

    }

    @FXML
    void onActionComfirm(ActionEvent event) {

    }

    @FXML
    void onKeyBalance(KeyEvent event) {

    }

}
