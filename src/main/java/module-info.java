module lk.ijse.flowershop {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires javafx.graphics;


    opens lk.ijse.flowershop.controller to javafx.fxml;
    opens lk.ijse.flowershop.dto to javafx.base;
    opens lk.ijse.flowershop.dto.tm to javafx.base;
    exports lk.ijse.flowershop;
}