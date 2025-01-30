package mainpkg;

public class CartItem {
    private String productName ;
    private float unitPrice ,vatAmount , totalAmount;
    private int vatRate , quantity ;

    public CartItem() {
        this.productName = "TBA";
        this.unitPrice = 0.00f;
        this.vatRate = 0;
        this.quantity = 0;
    }

    public CartItem(String productName, float unitPrice, int vatRate, int quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.vatRate = vatRate;
        this.quantity = quantity;
        this.vatAmount = unitPrice * vatRate / 100 ;
        this.totalAmount = (unitPrice + vatAmount) * quantity ;
    }

    public String getProductName() {
        return productName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getVatRate() {
        return vatRate;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getVatAmount() {
        return vatAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }
    

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setVatAmount(float vatAmount) {
        this.vatAmount = vatAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }


    
    
    
}
