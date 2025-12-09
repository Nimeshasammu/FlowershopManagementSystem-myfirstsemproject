package lk.ijse.flowershop.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardPageController {

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

}
