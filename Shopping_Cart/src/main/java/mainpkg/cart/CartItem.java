package mainpkg.cart;

import javafx.scene.control.CheckBox;

public class CartItem{
    private CheckBox check ;
    private Item item ;
    private int add , total;

    public CartItem(Item item, int add) {
        this.check = new CheckBox();
        this.item = item;
        this.add = add;
        this.total = this.item.getPrice() * this.add;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "check=" + check +
                ", item=" + item +
                ", add=" + add +
                ", total=" + total +
                '}';
    }
}
