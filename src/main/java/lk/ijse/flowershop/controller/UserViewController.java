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

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML private AnchorPane ancMainContainer;
    @FXML private Button btnDashboard, btnAllFlowers, btnOrders, btnPayment,
            btnCustomer, btnEmployee, btnSupplier, btnAdminManage,
            btnReport, btnLogout;

    @FXML private Label lblDate, lblTime;

    @FXML private ImageView pngDashboard, pngAllFlowers, pngOrder, pngPayment,
            pngCustomer, pngEmployee, pngSupplier,
            pngAdminManage, pngReport, pngLogout;

    // ================= BUTTON ACTIONS =================

    @FXML
    void onDashboard(ActionEvent event) {
        resetOtherPages();
        changePage1(btnDashboard, "/images/dashboard(1).png", pngDashboard);
        navigateTo("/view/DashboardPage.fxml");
    }

    @FXML
    void onAllFlowers(ActionEvent event) {
        resetOtherPages();
        changePage1(btnAllFlowers, "/images/flower(1).png", pngAllFlowers);
        navigateTo("/view/ProductPage.fxml");
    }

    @FXML
    void onOrders(ActionEvent event) {
        resetOtherPages();
        changePage1(btnOrders, "/images/order(1).png", pngOrder);
        navigateTo("/view/OrderPage.fxml");
    }

    @FXML
    void onPayment(ActionEvent event) {
        resetOtherPages();
        changePage1(btnPayment, "/images/payment(1).png", pngPayment);
        navigateTo("/view/Payment.fxml");
    }

    @FXML
    void onCustomer(ActionEvent event) {
        resetOtherPages();
        changePage1(btnCustomer, "/images/customer(1).png", pngCustomer);
        navigateTo("/view/CustomerPage.fxml");
    }

    @FXML
    void onEmployee(ActionEvent event) {
        resetOtherPages();
        changePage1(btnEmployee, "/images/employee(1).png", pngEmployee);
        navigateTo("/view/EmployeePage.fxml");
    }

    @FXML
    void onSupplier(ActionEvent event) {
        resetOtherPages();
        changePage1(btnSupplier, "/images/supplier(1).png", pngSupplier);
        navigateTo("/view/SupplierPage.fxml");
    }

    @FXML
    void onAdminManage(ActionEvent event) {
        resetOtherPages();
        changePage1(btnAdminManage, "/images/admin(1).png", pngAdminManage);
        navigateTo("/view/VerifySuperAdmin.fxml");
    }

    @FXML
    void onReport(ActionEvent event) {
        resetOtherPages();
        changePage1(btnReport, "/images/report(1).png", pngReport);
        navigateTo("/view/ReportPage.fxml");
    }

    @FXML
    void onLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Session.setCurrentUser(null);
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                    ancMainContainer.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // ================= NAVIGATION =================

    private void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            pane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            pane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            ancMainContainer.getChildren().add(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= IMAGE FIX METHODS =================

    private void changePage(String imagePath, ImageView imageView) {
        InputStream stream = getClass().getResourceAsStream(imagePath);
        if (stream != null) {
            imageView.setImage(new Image(stream));
        } else {
            System.out.println("Image not found: " + imagePath);
        }
    }

    private void changePage1(Button button, String imagePath, ImageView imageView) {
        changePage(imagePath, imageView);
        setActiveButtonStyle(button);
    }

    // ================= STYLES =================

    private void resetOtherPages() {
        resetButtonStyle(btnDashboard);
        resetButtonStyle(btnAllFlowers);
        resetButtonStyle(btnOrders);
        resetButtonStyle(btnPayment);
        resetButtonStyle(btnCustomer);
        resetButtonStyle(btnEmployee);
        resetButtonStyle(btnSupplier);
        resetButtonStyle(btnAdminManage);
        resetButtonStyle(btnReport);
        resetButtonStyle(btnLogout);

        changePage("/images/dashboard.jpeg", pngDashboard);
        changePage("/images/flower.png", pngAllFlowers);
        changePage("/images/order.png", pngOrder);
        changePage("/images/payment.png", pngPayment);
        changePage("/images/customer.png", pngCustomer);
        changePage("/images/employee.png", pngEmployee);
        changePage("/images/supplier.png", pngSupplier);
        changePage("/images/admin.png", pngAdminManage);
        changePage("/images/report.jpeg", pngReport);
        changePage("/images/logout.jpeg", pngLogout);
    }

    private void resetButtonStyle(Button button) {
        button.setStyle("-fx-text-fill:#800080; -fx-background-color:#ffd3ef; -fx-border-color:#800080;");
    }

    private void setActiveButtonStyle(Button button) {
        button.setStyle("-fx-text-fill:white; -fx-background-color:#800080;");
    }

    // ================= INITIALIZE =================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        navigateTo("/view/DashboardPage.fxml");

        UserDto user = Session.getCurrentUser();
        if (user != null) {
            switch (user.getRole()) {
                case "Manager" -> btnAdminManage.setDisable(true);
                case "Receptionist" -> {
                    btnPayment.setDisable(true);
                    btnAdminManage.setDisable(true);
                }
            }
        }

        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy")));
        DateTimeUtil.updateRealTime(lblTime);
    }

    @FXML
    void onLoginUser(MouseEvent event) {}
}
