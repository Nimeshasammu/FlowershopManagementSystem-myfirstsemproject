package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.flowershop.dto.CustomerDto;
import lk.ijse.flowershop.model.CustomerModel;

import java.sql.SQLException;

public class CustomerPageController {

    @FXML
    private TableColumn<CustomerDto, String> colAddress;

    @FXML
    private TableColumn<CustomerDto, String> colContact;

    @FXML
    private TableColumn<CustomerDto, String> colEmail;

    @FXML
    private TableColumn<CustomerDto, Integer> colId;

    @FXML
    private TableColumn<CustomerDto, String> colJoinDate;

    @FXML
    private TableColumn<CustomerDto, String> colName;

    @FXML
    private TableView<CustomerDto> tblCustomer;

    @FXML
    private TextField textAddress;

    @FXML
    private TextField textContact;

    @FXML
    private TextField textDate;

    @FXML
    private TextField textEmail;

    @FXML
    private Label textId;

    @FXML
    private TextField textName;

    private final CustomerModel customerModel = new CustomerModel();

    @FXML
    public void initialize() {
        setCellValueFactory();
        setGenerateId();
        loadAllCustomers();
        tableSelectListener();
    }
    private void setGenerateId() {
        try {
            String lastId = customerModel.getLastCustomerId();
            int newId = 1;

            if (lastId != null && lastId.startsWith("C")) {
                newId = Integer.parseInt(lastId.substring(1)) + 1;
            }

            String formattedId = String.format("C%03d", newId);
            textId.setText(formattedId);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("register_date"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerDto> list = FXCollections.observableArrayList();
        try {
            list.addAll(customerModel.getAllCustomers());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblCustomer.setItems(list);
    }

    private void tableSelectListener() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textId.setText(String.valueOf(newVal.getCus_id()));
                textName.setText(newVal.getCus_name());
                textEmail.setText(newVal.getEmail());
                textContact.setText(newVal.getContact_num());
                textAddress.setText(newVal.getAddress());
                textDate.setText(newVal.getRegister_date());
            }
        });
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
    @FXML
    void onActionSave(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }
        try {
            CustomerDto dto = new CustomerDto(
                    textId.getText(),
                    textName.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText(),
                    textDate.getText()
            );

            boolean isSaved = customerModel.customerSave(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
                clearFields();
                loadAllCustomers();
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
            CustomerDto dto = new CustomerDto(
                    textId.getText(),
                    textName.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText(),
                    textDate.getText()
            );

            boolean isUpdated = customerModel.customerUpdate(dto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully!").show();
                clearFields();
                loadAllCustomers();
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
            CustomerDto dto = new CustomerDto();
            dto.setCus_id(textId.getText());

            boolean isDeleted = customerModel.customerDelete(dto);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully!").show();
                clearFields();
                loadAllCustomers();
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
        textDate.clear();
        tblCustomer.getSelectionModel().clearSelection();
    }
}
