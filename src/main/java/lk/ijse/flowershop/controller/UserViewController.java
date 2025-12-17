package lk.ijse.flowershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import lk.ijse.flowershop.dto.Session;
import lk.ijse.flowershop.dto.UserDto;
import lk.ijse.flowershop.util.DateTimeUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private AnchorPane ancMainContainer;

    @FXML
    private AnchorPane ancUserView;

    @FXML
    private Button btnAdminManage;

    @FXML
    private Button btnBooking;

    @FXML
    private Button btnCourse;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnInstructor;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnStudent;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private ImageView pngAdminManage;

    @FXML
    private ImageView pngBooking;

    @FXML
    private ImageView pngCourse;

    @FXML
    private ImageView pngDashboard;

    @FXML
    private ImageView pngInstructor;

    @FXML
    private ImageView pngLogout;

    @FXML
    private ImageView pngPayment;

    @FXML
    private ImageView pngRegister;

    @FXML
    private ImageView pngStudent;

    @FXML
    void onAdminManage(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnAdminManage, "/images/admin.png", pngAdminManage);
        navigateTo("/view/VerifySuperAdmin.fxml");
    }

    @FXML
    void onBooking(ActionEvent event) {

    }

    @FXML
    void onCourse(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnDashboard, "/images/dashboard.png", pngDashboard);
        navigateTo("/view/SupplierPage.fxml");
    }

    @FXML
    void onDashboard(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnDashboard, "/images/dashboard.png", pngDashboard);
        navigateTo("/view/DashboardPage.fxml");
    }

    @FXML
    void onInstructor(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnDashboard, "/images/dashboard.png", pngDashboard);
        navigateTo("/view/EmployeePage.fxml");
    }

    @FXML
    void onLoginUser(MouseEvent event) {

    }

    @FXML
    void onLogout(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnDashboard, "/images/logout.png", pngDashboard);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);

        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Clear the session
                Session.setCurrentUser(null);

                try {
                    // Load the login page
                    AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                    ancMainContainer.getScene().setRoot(loginPane);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to load login page", ButtonType.OK).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void onPayment(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnDashboard, "/images/dashboard.png", pngDashboard);
        navigateTo("/view/CustomerPage.fxml");
    }

    @FXML
    void onRegistration(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnAdminManage, "/images/admin.png", pngAdminManage);
        navigateTo("/view/ProductPage.fxml");
    }

    @FXML
    void onStudent(ActionEvent event) {
        resetOtherPages();
//        changePage1(btnAdminManage, "/images/admin.png", pngAdminManage);
        navigateTo("/view/OrderPage.fxml");
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


    private void resetOtherPages() {
        resetButtonStyle(btnDashboard);
        resetButtonStyle(btnRegister);
        resetButtonStyle(btnStudent);
        resetButtonStyle(btnCourse);
        resetButtonStyle(btnBooking);
        resetButtonStyle(btnPayment);
        resetButtonStyle(btnInstructor);
        resetButtonStyle(btnAdminManage);
        resetButtonStyle(btnLogout);

//        changePage("/images/dashboard(1).png", pngDashboard);
//        changePage("/images/registration(1).png", pngRegister);
//        changePage("/images/students(1).png", pngStudent);
//        changePage("/images/course(1).png", pngCourse);
//        changePage("/images/booking(1).png", pngBooking);
//        changePage("/images/payment(1).png", pngPayment);
//        changePage("/images/report(1).png", pngInstructor);
//        changePage("/images/admin(1).png", pngAdminManage);
//        changePage("/images/logout(1).png", pngLogout);

    }

    private void resetButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: #800080; -fx-background-color:  #ffd3ef; -fx-border-color: #800080; -fx-border-radius: 10px; -fx-background-radius: 10;");
    }

    private void setActiveButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: white; -fx-background-color: #800080; -fx-border-color: #800080; -fx-border-radius: 10px; -fx-background-radius: 10;");
    }

    private void changePage(String imagePath, ImageView imageView) {
        imageView.setImage(new Image(imagePath));
    }

    private void changePage1(Button button, String imagePath, ImageView imageView) {
        imageView.setImage(new Image(imagePath));
        setActiveButtonStyle(button);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/DashboardPage.fxml");
        UserDto user = Session.getCurrentUser();
        String role = user.getRole();

        switch (role) {
            case "Admin":
                break;
            case "Manager":
                btnAdminManage.setDisable(true);
                break;
            case "Receptionist":
                btnBooking.setDisable(true);
                btnPayment.setDisable(true);
                btnCourse.setDisable(true);
                btnInstructor.setDisable(true);
                btnAdminManage.setDisable(true);
                break;
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        lblDate.setText(currentDate.format(formatter));
        DateTimeUtil.updateRealTime(lblTime);

    }
}
