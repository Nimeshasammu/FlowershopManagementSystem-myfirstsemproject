package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.ItemDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public boolean itemSave(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Item(item_name, unit_price, img_src, item_color, quantity) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,
                itemDto.getItem_name(),
                itemDto.getUnit_price(),
                itemDto.getImg_src(),
                itemDto.getItem_color(),
                itemDto.getQuantity());
    }

    public boolean itemUpdate(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET item_name=?, unit_price=?, img_src=?, item_color=?, quantity=? WHERE item_id=?";
        return CrudUtil.execute(sql,
                itemDto.getItem_name(),
                itemDto.getUnit_price(),
                itemDto.getImg_src(),
                itemDto.getItem_color(),
                itemDto.getQuantity(),
                itemDto.getItem_id());
    }

    public boolean itemDelete(int itemId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Item WHERE item_id=?";
        return CrudUtil.execute(sql, itemId);
    }

    public List<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Item";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<ItemDto> itemList = new ArrayList<>();

        while (resultSet.next()) {
            itemList.add(new ItemDto(
                    resultSet.getInt("item_id"),
                    resultSet.getString("item_name"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getString("img_src"),
                    resultSet.getString("item_color"),
                    resultSet.getInt("quantity")
            ));
        }
        return itemList;
    }
}
