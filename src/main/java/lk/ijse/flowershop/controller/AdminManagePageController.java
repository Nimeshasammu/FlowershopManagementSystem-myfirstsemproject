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
import lk.ijse.flowershop.dto.UserDto;
import lk.ijse.flowershop.model.UserModel;

import java.sql.SQLException;

public class AdminManagePageController {

    @FXML
    private TableColumn<UserDto, Integer> colId;

    @FXML
    private TableColumn<UserDto, String> colUsername;

    @FXML
    private TableColumn<UserDto, String> colPassword;

    @FXML
    private TableColumn<UserDto, String> colEmail;

    @FXML
    private TableColumn<UserDto, String> colJobRole;

    @FXML
    private TableColumn<UserDto, Integer> colEmployeeID;

    @FXML
    private TableView<UserDto> tblUser;

    @FXML
    private TextField textId;

    @FXML
    private TextField textUsername;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textJobRole;

    @FXML
    private TextField textEmployeeId;

    private final UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllUsers();
        tableSelectListener();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
    }

    private void loadAllUsers() {
        ObservableList<UserDto> list = FXCollections.observableArrayList();
        try {
            list.addAll(userModel.getAllUsers());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        tblUser.setItems(list);
    }

    private void tableSelectListener() {
        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                textId.setText(String.valueOf(newVal.getUser_id()));
                textUsername.setText(newVal.getUser_name());
                textPassword.setText(newVal.getPassword());
                textEmail.setText(newVal.getEmail());
                textJobRole.setText(newVal.getRole());
                textEmployeeId.setText(String.valueOf(newVal.getEmp_id()));
            }
        });
    }


    @FXML
    void onActionSave(ActionEvent event) {
        try {
            UserDto dto = new UserDto(
                    0, // auto increment
                    textUsername.getText(),
                    textPassword.getText(),
                    textEmail.getText(),
                    textJobRole.getText(),
                    Integer.parseInt(textEmployeeId.getText())
            );

            boolean isSaved = userModel.userSave(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully!").show();
                clearFields();
                loadAllUsers();
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
            UserDto dto = new UserDto(
                    Integer.parseInt(textId.getText()),
                    textUsername.getText(),
                    textPassword.getText(),
                    textEmail.getText(),
                    textJobRole.getText(),
                    Integer.parseInt(textEmployeeId.getText())
            );

            boolean isUpdated = userModel.userUpdate(dto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User Updated Successfully!").show();
                clearFields();
                loadAllUsers();
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
            int userId = Integer.parseInt(textId.getText());

            boolean isDeleted = userModel.userDelete(userId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").show();
                clearFields();
                loadAllUsers();
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
        textUsername.clear();
        textPassword.clear();
        textEmail.clear();
        textJobRole.clear();
        textEmployeeId.clear();
        tblUser.getSelectionModel().clearSelection();
    }
}
