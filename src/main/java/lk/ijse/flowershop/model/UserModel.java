package lk.ijse.flowershop.model;

import lk.ijse.flowershop.db.DBConnection;
import lk.ijse.flowershop.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserModel {
    public static UserDto searchUser(String userName, String password) {
        String sql = "SELECT * FROM User WHERE userName = ? AND password = ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
