package lk.ijse.flowershop.model;

import lk.ijse.flowershop.db.DBConnection;
import lk.ijse.flowershop.dto.UserDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public static UserDto searchUser(String userName, String password) {
        String sql = "SELECT * FROM User WHERE user_name = ? AND password = ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString("user_name"),
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
    public boolean userSave(UserDto userDto) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO User(user_id,user_name, password, email, role, emp_id) VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                userDto.getUser_id(),
                userDto.getUser_name(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRole(),
                userDto.getEmp_id()
        );
    }

    public boolean userUpdate(UserDto userDto) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE User SET user_id=?, user_name=?, password=?, email=?, role=?, emp_id=? WHERE user_id=?";

        return CrudUtil.execute(
                sql,
                userDto.getUser_id(),
                userDto.getUser_name(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRole(),
                userDto.getEmp_id(),
                userDto.getUser_id()
        );
    }

    public boolean userDelete(String userId) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM User WHERE user_id=?";

        return CrudUtil.execute(sql, userId);
    }

    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM User";
        ResultSet rs = CrudUtil.execute(sql);

        ArrayList<UserDto> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new UserDto(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("emp_id")
            ));
        }
        return list;
    }

    public UserDto findByUsername(String username) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM User WHERE user_name=?";
        ResultSet rs = CrudUtil.execute(sql, username);

        if (rs.next()) {
            return new UserDto(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("emp_id")
            );
        }
        return null;
    }
    public String getLastUserId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT MAX(user_id) FROM User";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

}
