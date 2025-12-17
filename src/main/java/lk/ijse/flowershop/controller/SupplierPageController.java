package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.flowershop.dto.SupplierDto;
import lk.ijse.flowershop.model.SupplierModel;

import java.sql.SQLException;

public class SupplierPageController {

    @FXML
    private TableColumn<SupplierDto, String> colAddress;

    @FXML
    private TableColumn<SupplierDto, String> colContact;

    @FXML
    private TableColumn<SupplierDto, String> colEmail;

    @FXML
    private TableColumn<SupplierDto, Integer> colId;

    @FXML
    private TableColumn<SupplierDto, String> colName;

    @FXML
    private TableView<SupplierDto> tblSupplier;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textContact;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textId;

    @FXML
    private TextField textName;

    private final SupplierModel supplierModel = new SupplierModel();

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        tableSelectListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierDto> list = FXCollections.observableArrayList();
        try {
            list.addAll(supplierModel.getAllSuppliers());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblSupplier.setItems(list);
    }

    private void tableSelectListener() {
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textId.setText(String.valueOf(newVal.getSupplier_id()));
                textName.setText(newVal.getName());
                textEmail.setText(newVal.getEmail());
                textContact.setText(newVal.getContact_num());
                textAddress.setText(newVal.getAddress());
            }
        });
    }

    @FXML
    void onActionSave(ActionEvent event) {
        try {
            SupplierDto dto = new SupplierDto(
                    Integer.parseInt(textId.getText()),
                    textName.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText()
            );

            boolean isSaved = supplierModel.supplierSave(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully!").show();
                clearFields();
                loadAllSuppliers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        try {
            SupplierDto dto = new SupplierDto(
                    Integer.parseInt(textId.getText()),
                    textName.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText()
            );

            boolean isUpdated = supplierModel.supplierUpdate(dto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!").show();
                clearFields();
                loadAllSuppliers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            SupplierDto dto = new SupplierDto();
            dto.setSupplier_id(Integer.parseInt(textId.getText()));

            boolean isDeleted = supplierModel.supplierDelete(dto);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully!").show();
                clearFields();
                loadAllSuppliers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionClear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        textId.clear();
        textName.clear();
        textEmail.clear();
        textContact.clear();
        textAddress.clear();
        tblSupplier.getSelectionModel().clearSelection();
    }
}
