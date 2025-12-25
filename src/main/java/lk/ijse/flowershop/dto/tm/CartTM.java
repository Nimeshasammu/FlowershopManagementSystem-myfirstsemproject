package lk.ijse.flowershop.dto.tm;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartTM {
    private String product;
    private int qty;
    private double price;

}

