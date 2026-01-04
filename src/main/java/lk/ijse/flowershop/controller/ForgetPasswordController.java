package lk.ijse.flowershop.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import lk.ijse.flowershop.dto.Session;
import lk.ijse.flowershop.dto.UserDto;
import lk.ijse.flowershop.model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ForgetPasswordController implements Initializable {

    @FXML
    private AnchorPane ancMainContainer;

    @FXML
    private Hyperlink lblError;

    @FXML
    private HBox lblUsername;

    @FXML
    private HBox lblEmail;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUserName;

    private static UserModel userModel = new UserModel();
    @FXML
    void onForgotPasswordAction(ActionEvent event) {
        navigateTo("/view/LoginPage.fxml");
    }

    @FXML
    void onKeyEmail(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                txtEmail.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }
    }

    @FXML
    void onSignAction(ActionEvent event) throws SQLException, ClassNotFoundException {
signIn();
    }

    public void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            ancMainContainer.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void onKeySingin(KeyEvent actionEvent) {
        if (actionEvent.getCode().toString().equals("ENTER")) {
            try {
                signIn();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }
    }

    private void signIn() throws SQLException, ClassNotFoundException {
        String userName = txtUserName.getText();
        String email = txtEmail.getText();

        if (userName.isEmpty() || email.isEmpty()) {
            showErrorWithTimeout();
            return;
        }
        UserDto user = userModel.searchUser1(userName, email);

        if (user != null) {
            Session.setCurrentUser(user);
            navigateTo("/view/UserView.fxml");
        } else {
            showErrorWithTimeout();
        }
    }

    private void showErrorWithTimeout() {
        lblError.setVisible(true);
        resetFieldStyles();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    lblError.setVisible(false);
                    resetFieldStyles1();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void resetFieldStyles() {
        lblUsername.setStyle("-fx-background-color: #dfe4ea; -fx-border-color: RED; -fx-border-radius: 10; -fx-background-radius: 10;");
        lblEmail.setStyle("-fx-background-color: #dfe4ea; -fx-border-color: RED; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    private void resetFieldStyles1() {
        lblUsername.setStyle("-fx-background-color: #ffd3ef; -fx-border-color:  #ff00ff; -fx-border-radius: 10; -fx-background-radius: 10;");
        lblEmail.setStyle("-fx-background-color: #ffd3ef; -fx-border-color:  #ff00ff; -fx-border-radius: 10; -fx-background-radius: 10;");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        javafx.application.Platform.runLater(() -> txtUserName.requestFocus());
    }
}
