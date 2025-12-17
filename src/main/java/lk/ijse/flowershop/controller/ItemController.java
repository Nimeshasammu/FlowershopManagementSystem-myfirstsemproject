package lk.ijse.flowershop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.flowershop.dto.ItemDto;
import lk.ijse.flowershop.dto.MyListener;

import java.util.Objects;

public class ItemController {

    public Label stockLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLable;
    @FXML
    private ImageView img;

    private ItemDto item;
    private MyListener myListener;
//    public static final String CURRENCY = "$";


    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(item);
    }

    public void setData(ItemDto item, MyListener myListener) {
        this.item = item;
        this.myListener = myListener;

        nameLabel.setText(item.getItem_name());
        stockLabel.setText(String.valueOf(item.getQuantity()));
        priceLable.setText(String.valueOf(item.getUnit_price()));

        Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(item.getImg_src()))
        );
        img.setImage(image);
    }
}
