package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Label textId;

    @FXML
    private TextField textName;

    private final SupplierModel supplierModel = new SupplierModel();

    @FXML
    public void initialize() {
        setCellValueFactory();
        setGenerateId();
        loadAllSuppliers();
        tableSelectListener();
    }
    private void setGenerateId() {
        try {
            String lastId = supplierModel.getLastSupplierId();
            int newId = 1;

            if (lastId != null && lastId.startsWith("S")) {
                newId = Integer.parseInt(lastId.substring(1)) + 1;
            }

            String formattedId = String.format("S%03d", newId);
            textId.setText(formattedId);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    private boolean validateInputs() {
        if (textName.getText().isEmpty() || textContact.getText().isEmpty() ||
                textEmail.getText().isEmpty() || textAddress.getText().isEmpty()) {
            showErrorMessage("All fields must be filled!");
            return false;
        }

        if (!isValidEmail(textEmail.getText())) {
            showErrorMessage("Invalid email format!");
            return false;
        }
        if (!isValidContact(textContact.getText())) {
            showErrorMessage("Contact number must be 10 digits!");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidContact(String contact) {
        return contact != null && contact.matches("^\\d{10}$");
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        if (!validateInputs()) {
            return;
        }
        try {
            SupplierDto dto = new SupplierDto(
                    textId.getText(),
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
        if (!validateInputs()) {
            return;
        }
        try {
            SupplierDto dto = new SupplierDto(
                    textId.getText(),
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
            dto.setSupplier_id(textId.getText());

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
        setGenerateId();
        textName.clear();
        textEmail.clear();
        textContact.clear();
        textAddress.clear();
        tblSupplier.getSelectionModel().clearSelection();
    }
}
