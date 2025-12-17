package lk.ijse.flowershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lk.ijse.flowershop.dto.ItemDto;
import lk.ijse.flowershop.dto.MyListener;
import lk.ijse.flowershop.model.ItemModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label fruitNameLable;
    @FXML
    private Label fruitPriceLabel;
    @FXML
    private ImageView fruitImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    private final ItemModel itemModel = new ItemModel();
    private final List<ItemDto> fruits = new ArrayList<>();
    private MyListener myListener;

    private void setChosenFruit(ItemDto item) {
//        fruitNameLable.setText(item.getItem_name());
//        fruitPriceLabel.setText(HelloApplication.CURRENCY + item.getUnit_price());
//
//        Image image = new Image(
//                getClass().getResourceAsStream(item.getImg_src())
//        );
//        fruitImg.setImage(image);
//
//        chosenFruitCard.setStyle(
//                "-fx-background-color: #" + item.getItem_color() + ";" +
//                        "-fx-background-radius: 30;"
//        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            fruits.addAll(itemModel.getAllItems());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (!fruits.isEmpty()) {
            setChosenFruit(fruits.get(0));

            myListener = item -> setChosenFruit(item);
        }

        int column = 0;
        int row = 1;

        try {
            for (ItemDto item : fruits) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/ItemPage.fxml")
                );
                AnchorPane pane = loader.load();

                ItemController controller = loader.getController();
                controller.setData(item, myListener);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionAddFlower(ActionEvent actionEvent) {

    }
}
