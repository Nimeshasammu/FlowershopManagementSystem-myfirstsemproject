package lk.ijse.flowershop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.flowershop.dto.EmployeeDto;
import lk.ijse.flowershop.model.EmployeeModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {

    @FXML
    private TableView<EmployeeDto> tblEmployee;

    @FXML
    private TableColumn<EmployeeDto, Integer> colId;

    @FXML
    private TableColumn<EmployeeDto, String> colName;

    @FXML
    private TableColumn<EmployeeDto, String> colNic;

    @FXML
    private TableColumn<EmployeeDto, String> colJobRole;

    @FXML
    private TableColumn<EmployeeDto, String> colEmail;

    @FXML
    private TableColumn<EmployeeDto, String> colContact;

    @FXML
    private TableColumn<EmployeeDto, String> colAddress;

    @FXML
    private TextField textId;
    @FXML
    private TextField textName;
    @FXML
    private TextField textNic;
    @FXML
    private TextField textJobRole;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textContact;
    @FXML
    private TextField textAddress;

    private final EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadAllEmployees();
        tableListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("job_role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
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
        if (!isValidNIC(textNic.getText())) {
            showErrorMessage("Invalid NIC format!");
            return false;
        }

        return true;
    }
    private boolean isValidNIC(String nic) {
        return nic != null && (nic.matches("^\\d{9}[vVxX]$") || nic.matches("^\\d{12}$"));
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

    private void loadAllEmployees() {
        try {
            ObservableList<EmployeeDto> list =
                    FXCollections.observableArrayList(employeeModel.getAllEmployee());
            tblEmployee.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textId.setText(String.valueOf(newVal.getEmp_id()));
                textName.setText(newVal.getName());
                textNic.setText(newVal.getNic());
                textJobRole.setText(newVal.getJob_role());
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
            EmployeeDto dto = new EmployeeDto(
                    0,
                    textName.getText(),
                    textNic.getText(),
                    textJobRole.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText()
            );

            boolean isSaved = employeeModel.employeeSave(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
                loadAllEmployees();
                clearFields();
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
            EmployeeDto dto = new EmployeeDto(
                    Integer.parseInt(textId.getText()),
                    textName.getText(),
                    textNic.getText(),
                    textJobRole.getText(),
                    textEmail.getText(),
                    textContact.getText(),
                    textAddress.getText()
            );

            boolean isUpdated = employeeModel.employeeUpdate(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated").show();
                loadAllEmployees();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            int empId = Integer.parseInt(textId.getText());
            boolean isDeleted = employeeModel.employeeDelete(empId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted").show();
                loadAllEmployees();
                clearFields();
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
        textNic.clear();
        textJobRole.clear();
        textEmail.clear();
        textContact.clear();
        textAddress.clear();
    }
}
