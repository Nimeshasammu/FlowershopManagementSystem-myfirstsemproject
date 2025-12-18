package lk.ijse.flowershop.dto.tm;

public class ProductTM {

    private String product;
    private int qty;
    private double price;

    public ProductTM(String product, int qty, double price) {
        this.product = product;
        this.qty = qty;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
