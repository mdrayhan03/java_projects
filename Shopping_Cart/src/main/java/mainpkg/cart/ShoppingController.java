package mainpkg.cart;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ShoppingController
{

    @javafx.fxml.FXML
    private GridPane itemlistGridpane;
    @javafx.fxml.FXML
    private ProgressBar loadingPB;
    @javafx.fxml.FXML
    private Label cartDesL;
    @javafx.fxml.FXML
    private Label cartItemNameL;
    @javafx.fxml.FXML
    private Label cartPriceL;
    @javafx.fxml.FXML
    private AnchorPane cartAnchor;
    @javafx.fxml.FXML
    private Spinner<Integer> fromSB;
    @javafx.fxml.FXML
    private TextField nameTF;
    @javafx.fxml.FXML
    private ComboBox<String> categoryCB;
    @javafx.fxml.FXML
    private Spinner<Integer> toSB;
    @javafx.fxml.FXML
    private Label totalunitL;
    @javafx.fxml.FXML
    private Spinner<Integer> cartItemSB;
    @javafx.fxml.FXML
    private ImageView cartItemIV;
    @javafx.fxml.FXML
    private Button shoppingButton;
    @javafx.fxml.FXML
    private Button addCartButton;

    ObservableList<Item> itemObservableList = FXCollections.observableArrayList() ;
    ObservableList<CartItem> cartObservableList = FXCollections.observableArrayList() ;
    Item showedItem ;
    MyListener myListener ;
    @javafx.fxml.FXML
    private ScrollPane itemSP;


    @javafx.fxml.FXML
    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        categoryCB.getItems().addAll("All","Fruit", "Food", "Grocery", "Device", "Vegetable") ;
        categoryCB.setValue("All") ;

        SpinnerValueFactory<Integer> valueFactoryf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        fromSB.setValueFactory(valueFactoryf);
        fromSB.getValueFactory().setValue(0) ;

        SpinnerValueFactory<Integer> valueFactoryt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        toSB.setValueFactory(valueFactoryt);
        toSB.getValueFactory().setValue(99999) ;

        itemObservableList = this.fetch_data() ;
        this.itemlist(itemObservableList);
    }

    public  void itemlist(ObservableList<Item> itemObservableList) throws IOException {
        int row = 1;
        int column = 0;

        for (Item i : itemObservableList) {
            myListener = new MyListener() {
                @Override
                public void onClick(Item item) throws FileNotFoundException {
                    cart_show(i);
                }
            };
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ItemFxml.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemController itemController = fxmlLoader.getController();
            itemController.setter(i, myListener);

            if (column == 3) {
                column = 0;
                row++;
            }
            itemlistGridpane.add(anchorPane, column++, row);

            itemlistGridpane.setMinWidth(Region.USE_COMPUTED_SIZE);
            itemlistGridpane.setPrefWidth(Region.USE_COMPUTED_SIZE);
            itemlistGridpane.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            itemlistGridpane.setMinHeight(Region.USE_COMPUTED_SIZE);
            itemlistGridpane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            itemlistGridpane.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(15));
        }
    }

    @javafx.fxml.FXML
    public void adminPageOnMouseClick(Event event) throws IOException {
        loadingPB.setProgress(-1.0);
        new Thread(() ->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                try {
                    this.adminPageMethod(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
//        this.adminPageMethod(event);
    }

    public void cart_show(Item i) throws FileNotFoundException {
        SpinnerValueFactory<Integer> valueFactoryc = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, i.getTotal(), 1);
        cartItemSB.setValueFactory(valueFactoryc);
        cartItemSB.getValueFactory().setValue(i.getTotal()) ;

        cartAnchor.setStyle("-fx-background-color: "+i.getColor()+"; -fx-background-radius:20; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0.0, 5, 5)");
        cartItemNameL.setText(i.getName());
        cartPriceL.setText("Price: "+Integer.toString(i.getPrice())+"tk per "+i.getUnit());
        cartDesL.setText(i.getDescription());
        Image img = new Image(new FileInputStream("src\\main\\resources\\media\\" + i.getPath())) ;
        cartItemIV.setImage(img);
        if (i.getTotal() == 0) {
            totalunitL.setText("--Stock out--");
            totalunitL.setTextFill(Color.RED);
            totalunitL.setAlignment(Pos.CENTER);
            addCartButton.setDisable(true);
        }
        else {
            totalunitL.setText("Number of item: "+i.getTotal());
            totalunitL.setTextFill(Color.BLACK);
            totalunitL.setAlignment(Pos.CENTER_LEFT);
            addCartButton.setDisable(false);
        }
        if (itemSP.isFocused()) {
            itemSP.setStyle("-fx-border-width:1; -fx-border-color:black;");
        }
        showedItem = i ;
    }

    private void adminPageMethod(Event event) throws IOException {
        Parent root = null ;
        FXMLLoader fxmlLoader = new FXMLLoader(ShoppingController.class.getResource("AdminFxml.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root) ;
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.show();
    }

    private ObservableList<Item> fetch_data() throws SQLException, ClassNotFoundException, IOException {
        DataBase db = new DataBase();
        ExcelControl excel = new ExcelControl() ;
//        return db.select_data() ;
        return excel.select_data() ;
    }

    @javafx.fxml.FXML
    public void filterOnMouseClick(Event event) throws IOException {
        itemlistGridpane.getChildren().clear();
        ObservableList<Item> filteredItemarr = FXCollections.observableArrayList() ;
        for (Item i : itemObservableList) {
            if (Objects.equals(i.getCatagory(), categoryCB.getValue())) {
                if (fromSB.getValue() <= i.getPrice() && i.getPrice() <= toSB.getValue()) {
                    if (nameTF.getText().isEmpty()) {
                        filteredItemarr.add(i);
                    }
                    else if (Objects.equals(nameTF.getText(), i.getName())) {
                        filteredItemarr.add(i) ;
                    }
                }
            }
            else if (Objects.equals(categoryCB.getValue(), "All")) {
                if (fromSB.getValue() <= i.getPrice() && i.getPrice() <= toSB.getValue()) {
                    if (nameTF.getText().isEmpty()) {
                        filteredItemarr.add(i);
                    }
                    else if (Objects.equals(nameTF.getText(), i.getName())) {
                        filteredItemarr.add(i) ;
                    }
                }
            }
        }
        this.itemlist(filteredItemarr);
    }

    @javafx.fxml.FXML
    public void shoppingButtonOnAction(ActionEvent actionEvent) throws IOException {
        for (CartItem ct : cartObservableList) {
            System.out.println(ct);
        }
        Parent root = null ;
        FXMLLoader fxmlLoader = new FXMLLoader(ShoppingController.class.getResource("BuyFxml.fxml"));
        root = fxmlLoader.load();
        BuyController bc = fxmlLoader.getController() ;
        bc.setter(cartObservableList);
        Scene scene = new Scene(root) ;
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Page");
        stage.show();
    }

    @javafx.fxml.FXML
    public void addCartButtonOnAction(ActionEvent actionEvent) {
        CartItem item = new CartItem(showedItem, cartItemSB.getValue()) ;
        cartObservableList.add(item) ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("Cart Item");
        alert.setHeaderText("Item Add");
        alert.setContentText("Item added into cart successfully");
        alert.showAndWait();
    }
}