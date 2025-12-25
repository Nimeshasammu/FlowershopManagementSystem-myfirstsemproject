package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.CustomerDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public boolean customerSave(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer(cus_id,cus_name,email,contact_num,address,register_date) VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(sql, customerDto.getCus_id(), customerDto.getCus_name(), customerDto.getEmail(), customerDto.getContact_num(), customerDto.getAddress(), customerDto.getRegister_date());
    }


    public boolean customerUpdate(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET cus_name=?,email=?,contact_num=?,address=?,register_date=? WHERE cus_id=?";
        return CrudUtil.execute(sql, customerDto.getCus_name(), customerDto.getEmail(), customerDto.getContact_num(), customerDto.getAddress(), customerDto.getRegister_date(), customerDto.getCus_id());
    }

    public boolean customerDelete(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE cus_id=?";
        return CrudUtil.execute(sql, customerDto.getCus_id());
    }

    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer");
        List<CustomerDto> customerDtoList = new ArrayList<>();
        while (resultSet.next()) {
            customerDtoList.add(new CustomerDto( resultSet.getString("cus_id"),
                    resultSet.getString("cus_name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact_num"),
                    resultSet.getString("address"),
                    resultSet.getString("register_date")));
        }
        return customerDtoList;
    }
    public String getLastCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(cus_id) FROM Customer");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT cus_id FROM Customer");
        List<String> customerIds = new ArrayList<>();

        while (resultSet.next()) {
            customerIds.add(resultSet.getString("cus_id"));
        }
        return customerIds;
    }

}