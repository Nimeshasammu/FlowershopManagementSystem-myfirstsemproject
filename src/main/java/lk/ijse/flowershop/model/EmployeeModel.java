package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.EmployeeDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public boolean employeeSave(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Employee(name, nic, job_role, email, contact_num, address) VALUES (?,?,?,?,?,?)";

        return CrudUtil.execute(
                sql,
                employeeDto.getName(),
                employeeDto.getNic(),
                employeeDto.getJob_role(),
                employeeDto.getEmail(),
                employeeDto.getContact_num(),
                employeeDto.getAddress()
        );
    }

    public boolean employeeUpdate(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Employee SET name=?, nic=?, job_role=?, email=?, contact_num=?, address=? WHERE emp_id=?";

        return CrudUtil.execute(
                sql,
                employeeDto.getName(),
                employeeDto.getNic(),
                employeeDto.getJob_role(),
                employeeDto.getEmail(),
                employeeDto.getContact_num(),
                employeeDto.getAddress(),
                employeeDto.getEmp_id()
        );
    }

    public boolean employeeDelete(int empId) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Employee WHERE emp_id=?";

        return CrudUtil.execute(sql, empId);
    }

    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM Employee";

        ResultSet rs = CrudUtil.execute(sql);
        ArrayList<EmployeeDto> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new EmployeeDto(
                    rs.getInt("emp_id"),
                    rs.getString("name"),
                    rs.getString("nic"),
                    rs.getString("job_role"),
                    rs.getString("email"),
                    rs.getString("contact_num"),
                    rs.getString("address")
            ));
        }

        return list;
    }
}
