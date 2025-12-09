module lk.ijse.flowershop {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires java.sql;


    opens lk.ijse.flowershop.controller to javafx.fxml;
    exports lk.ijse.flowershop;
}