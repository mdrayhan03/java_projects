package mainpkg.cart;

import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class Item {
    private String name, description, catagory, unit, color, path ;
    private int id, price, total;
    Button edit, delete;

    public Item(int id, String name, String description, String catagory, String unit, String color, String path, int price, int total) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.catagory = catagory;
        this.unit = unit;
        this.color = color;
        this.path = path;
        this.price = price;
        this.total = total;
        this.edit = new Button("Edit");
        this.delete = new Button("Delete");
        this.editAction();
        this.deleteAction();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPrice() {
        return price;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", catagory='" + catagory + '\'' +
                ", unit='" + unit + '\'' +
                ", color='" + color + '\'' +
                ", path='" + path + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", total=" + total +
                '}';
    }

    public void editAction() {}
//    {
//        this.edit.setOnAction((ActionEvent event) -> {
//            System.out.println("Edit") ;
//            AdminController ad = new AdminController() ;
//        });
//    }

    public void deleteAction() {
        this.delete.setOnAction((ActionEvent event) -> {
            try {
//                DataBase db = new DataBase() ;
//                db.delete_data(this.id);
                ExcelControl excel = new ExcelControl() ;
                excel.delete_data(this.id);
                System.out.println("Delete");
              }
//            catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
