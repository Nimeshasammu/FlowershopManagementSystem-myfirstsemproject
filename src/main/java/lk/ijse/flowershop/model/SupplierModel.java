package lk.ijse.flowershop.model;

import lk.ijse.flowershop.dto.SupplierDto;
import lk.ijse.flowershop.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public boolean supplierSave(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Supplier(supplier_id, name, email, contact_num, address) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,
                supplierDto.getSupplier_id(),
                supplierDto.getName(),
                supplierDto.getEmail(),
                supplierDto.getContact_num(),
                supplierDto.getAddress());
    }

    public boolean supplierUpdate(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Supplier SET  name=?, email=?, contact_num=?, address=? WHERE supplier_id=?";
        return CrudUtil.execute(sql,
                supplierDto.getName(),
                supplierDto.getEmail(),
                supplierDto.getContact_num(),
                supplierDto.getAddress(),
                supplierDto.getSupplier_id());
    }

    public boolean supplierDelete(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Supplier WHERE supplier_id=?";
        return CrudUtil.execute(sql, supplierDto.getSupplier_id());
    }

    public List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Supplier";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<SupplierDto> supplierDtoList = new ArrayList<>();

        while (resultSet.next()) {
            supplierDtoList.add(new SupplierDto(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact_num"),
                    resultSet.getString("address")
            ));
        }
        return supplierDtoList;
    }
    public String getLastSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(supplier_id) FROM Supplier");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
