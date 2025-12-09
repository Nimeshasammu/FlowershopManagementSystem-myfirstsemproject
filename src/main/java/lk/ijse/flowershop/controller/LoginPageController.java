package lk.ijse.flowershop.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import lk.ijse.flowershop.dto.Session;
import lk.ijse.flowershop.dto.UserDto;
import lk.ijse.flowershop.model.UserModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private HBox lblUsername, lblPassword;

    @FXML
    private AnchorPane ancMainContainer;
    @FXML
    private Hyperlink lblError;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView showPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtVisiblePassword;

    private boolean isPasswordVisible = false;


    @FXML
    void PasswordVisibility(MouseEvent event) {
        if (isPasswordVisible) {
            passwordField.setText(txtVisiblePassword.getText());
            txtVisiblePassword.setVisible(false);
            txtVisiblePassword.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            isPasswordVisible = false;
        } else {
            txtVisiblePassword.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            txtVisiblePassword.setVisible(true);
            txtVisiblePassword.setManaged(true);
            isPasswordVisible = true;
        }

    }

    @FXML
    void onForgotPasswordAction(ActionEvent event) {

    }

    @FXML
    void onKeyPassword(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                passwordField.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }
    }

    @FXML
    void onKeySingin(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                signIn();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }
    }

    private void signIn() throws IOException {
        String userName = txtUserName.getText();
        String password;
        if (!isPasswordVisible) {
            password = passwordField.getText();
        } else {
            password = txtVisiblePassword.getText();
        }

        if (userName.isEmpty() || password.isEmpty()) {
            showErrorWithTimeout();
            return;
        }
        UserDto user = UserModel.searchUser(userName, password);


        if (user != null) {
            Session.setCurrentUser(user);

            navigateTo("/view/UserView.fxml");
        } else {
            showErrorWithTimeout();
        }
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


    @FXML
    void onSignAction(ActionEvent event) throws IOException {
        signIn();
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
        lblPassword.setStyle("-fx-background-color: #dfe4ea; -fx-border-color: RED; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    private void resetFieldStyles1() {
        lblUsername.setStyle("-fx-background-color: #ffd3ef; -fx-border-color:  #ff00ff; -fx-border-radius: 10; -fx-background-radius: 10;");
        lblPassword.setStyle("-fx-background-color: #ffd3ef; -fx-border-color:  #ff00ff; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        javafx.application.Platform.runLater(() -> txtUserName.requestFocus());
    }
}
