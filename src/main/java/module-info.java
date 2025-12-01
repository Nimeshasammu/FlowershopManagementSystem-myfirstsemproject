module lk.ijse.flowershop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires lk.ijse.flowershop;
    requires java.desktop;


    opens lk.ijse.flowershop.controller to javafx.fxml;
    exports lk.ijse.flowershop;
}