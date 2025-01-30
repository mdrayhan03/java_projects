module mainpkg.cart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires mysql.connector.java;
    requires kernel;
    requires layout;


    opens mainpkg.cart to javafx.fxml;
    exports mainpkg.cart;
}