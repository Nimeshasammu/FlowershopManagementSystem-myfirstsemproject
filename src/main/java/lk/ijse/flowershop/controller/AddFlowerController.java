package lk.ijse.flowershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.flowershop.dto.ItemDto;
import lk.ijse.flowershop.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddFlowerController implements Initializable {

    @FXML
    private Label txtId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtImgSrc;

    @FXML
    private TextField txtItemColor;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    private final ItemModel itemModel = new ItemModel();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            // Validation
            if (txtName.getText().isEmpty() ||
                    txtUnitPrice.getText().isEmpty() ||
                    txtQuantity.getText().isEmpty()) {

                new Alert(Alert.AlertType.WARNING, "Please fill all required fields!").show();
                return;
            }

            String itemId = txtId.getText();
            String itemName = txtName.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            String imgSrc = "/img/"+txtImgSrc.getText();
            String itemColor = txtItemColor.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());

            ItemDto item = new ItemDto(
                    itemId,
                    itemName,
                    unitPrice,
                    imgSrc,
                    itemColor,
                    quantity
            );

            boolean isItemSaved = itemModel.itemSave(item);

            if (isItemSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Flower saved successfully!").show();
                closeWindow(event);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save flower!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format!").show();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGenerateId();
    }

    private void setGenerateId() {
        try {
            String lastId = itemModel.getLastItemId();
            int newId = 1;

            if (lastId != null && lastId.startsWith("I")) {
                newId = Integer.parseInt(lastId.substring(1)) + 1;
            }

            txtId.setText(String.format("I%03d", newId));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
