package lk.ijse.flowershop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.flowershop.model.ItemModel;
import lk.ijse.flowershop.model.OrderDetailsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCapacity;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colOrderNo;

    @FXML
    private TableColumn<?, ?> colProcuctName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private Label lblCurrentStock;

    @FXML
    private Label lblProduction;

    @FXML
    private Label lblSale;

    @FXML
    private Label lblTransport;

    @FXML
    private LineChart<?, ?> salesChart;

    @FXML
    private TableView<?> tableOrder;

    @FXML
    private TableView<?> tableVehical;

    private static OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    private static ItemModel itemModel = new ItemModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDashboardStats();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDashboardStats() throws SQLException, ClassNotFoundException {
        String sale = String.valueOf(orderDetailsModel.todaySale());
        lblSale.setText("Rs. "+sale);
//        String production =itemModel.todayAddedStock();
        String production = "120";
        lblProduction.setText(production);
//        String transport=deliverModel.getTodayTransportTotal();
        String transport = "100";
        lblTransport.setText(transport);
        String stock = String.valueOf(itemModel.currentStock());
        lblCurrentStock.setText(stock);
    }
}
