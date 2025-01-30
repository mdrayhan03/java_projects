package mainpkg.cart;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ItemController
{
    @javafx.fxml.FXML
    private Label priceL;
    @javafx.fxml.FXML
    private Label itemNameL;
    Item item ;
    MyListener myListener ;
    @javafx.fxml.FXML
    private AnchorPane anchorpane;
    @javafx.fxml.FXML
    private ImageView itemIV;

    public void setter(Item i, MyListener myListener) throws FileNotFoundException {
        item = i ;
        this.myListener = myListener ;
        itemNameL.setText(item.getName());
        priceL.setText(Integer.toString(item.getPrice()) + "tk");
        Image img = new Image(new FileInputStream("src\\main\\resources\\media\\" + i.getPath())) ;
        itemIV.setImage(img);
//        anchorpane.setStyle("-fx-background-color: "+i.getColor()+"; -fx-background-radius:10; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0.0, 5, 5)");
//        priceL.setText("99999");
    }

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void mouseenter(Event event) {
    }

    @javafx.fxml.FXML
    public void anchoronmouseclick(Event event) throws IOException {
        myListener.onClick(item);
    }
}