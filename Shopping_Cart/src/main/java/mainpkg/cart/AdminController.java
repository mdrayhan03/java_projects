package mainpkg.cart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AdminController
{
    @FXML
    private AnchorPane centerAnchorpane;
    @FXML
    private TextField adminIdTF;
    @FXML
    private PasswordField adminPasswordPF;
    @FXML
    private Spinner<Integer> priceSB;
    @FXML
    private TextField nameTF;
    @FXML
    private ComboBox<String> unitCB;
    @FXML
    private ColorPicker colorPK;
    @FXML
    private ComboBox<String> catagoryCB;
    @FXML
    private ImageView photoIV;
    @FXML
    private TextArea descriptionTA;
    @FXML
    private TableColumn<Item, Integer> priceTC;
    @FXML
    private TableView<Item> itemsTV;
    @FXML
    private TableColumn<Item, Button> deleteTC;
    @FXML
    private TableColumn<Item, String> nameTC;
    @FXML
    private TableColumn<Item, String> catagoryTC;
    @FXML
    private TableColumn<Item, String> unitTC;
    @FXML
    private TableColumn<Item, Integer> totalTC;
    @FXML
    private Label totalL;
    @FXML
    private Label deviceL;
    @FXML
    private Label fruitL;
    @FXML
    private Label groceryL;
    @FXML
    private Label vegetableL;
    @FXML
    private Label foodL;
    @FXML
    private Spinner<Integer> filterToSB;
    @FXML
    private Spinner<Integer> filterFromSB;
    @FXML
    private Spinner<Integer> totalunitSB;
    @FXML
    private ComboBox<String> filtercatagoryCB;

    ObservableList<Item> itemObservableArray = FXCollections.observableArrayList(); ;
    String filename ;
    File f ;
    DataBase db ;
    ExcelControl excel ;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException, IOException {
        db = new DataBase() ;
        excel = new ExcelControl() ;
        catagoryCB.getItems().addAll("Fruit", "Food", "Grocery", "Device", "Vegetable") ;
        catagoryCB.setValue("--Select--") ;
        filtercatagoryCB.getItems().addAll("All", "Fruit", "Food", "Grocery", "Device", "Vegetable") ;
        filtercatagoryCB.setValue("All") ;
        unitCB.getItems().addAll("kg", "pound", "piece") ;
        unitCB.setValue("--Select--") ;

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        priceSB.setValueFactory(valueFactory);
        priceSB.getValueFactory().setValue(0) ;

        SpinnerValueFactory<Integer> valueFactoryf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        filterFromSB.setValueFactory(valueFactoryf);
        filterFromSB.getValueFactory().setValue(0) ;

        SpinnerValueFactory<Integer> valueFactoryt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        filterToSB.setValueFactory(valueFactoryt);
        filterToSB.getValueFactory().setValue(99999) ;

        SpinnerValueFactory<Integer> valueFactoryu = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 1);
        totalunitSB.setValueFactory(valueFactoryu);
        totalunitSB.getValueFactory().setValue(0) ;

        nameTC.setCellValueFactory(new PropertyValueFactory<>("name")) ;
        unitTC.setCellValueFactory(new PropertyValueFactory<>("unit")) ;
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price")) ;
        catagoryTC.setCellValueFactory(new PropertyValueFactory<>("catagory")) ;
        totalTC.setCellValueFactory(new PropertyValueFactory<>("total")) ;
        deleteTC.setCellValueFactory(new PropertyValueFactory<>("delete")) ;

        this.loadinfo();

    }

    @FXML
    public void adminLoginButtonOnAction(ActionEvent actionEvent) {
        centerAnchorpane.setVisible(true);
    }

    @FXML
    public void addNewItemButtonOnAction(ActionEvent actionEvent) throws IOException {
        String name, catagory, unit, color, description, path ;
        int price, total;
        Color c ;

        name = nameTF.getText() ;
        catagory = catagoryCB.getValue() ;
        unit = unitCB.getValue() ;
        c = colorPK.getValue() ;
        color = toHexString(c) ;
        description = descriptionTA.getText() ;
        price = priceSB.getValue() ;
        path = filename ;
        total = totalunitSB.getValue() ;

        excel.insert_data(name, description, catagory, unit, color, path, price, total);
//        db.insert_data(name, description, catagory, unit, color, path, price, total);
//        itemObservableArray.add(new Item(id, name, description, catagory, unit, color, path, price)) ;
        this.loadinfo() ;

        nameTF.clear();
        catagoryCB.setValue("--Select--");
        unitCB.setValue("--Select--");
        priceSB.getValueFactory().setValue(0) ;
        descriptionTA.clear();
    }

    private String toHexString(Color color) {
        // Get the RGB values and format them as hex
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        int a = (int) (color.getOpacity() * 255); // Optional for alpha

        // If alpha is fully opaque, ignore it
        if (a == 255) {
            return String.format("#%02X%02X%02X", r, g, b);
        } else {
            return String.format("#%02X%02X%02X%02X", r, g, b, a);
        }
    }

    @FXML
    public void importImgButtonOnAction(ActionEvent actionEvent) throws FileNotFoundException {
        ArrayList<String> fileTypeList = new ArrayList<>() ;
        fileTypeList.add("*.jpeg");
        fileTypeList.add("*.png");
        FileChooser fc = new FileChooser() ;
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", fileTypeList));
        f = fc.showOpenDialog(null);
        Path path = Paths.get(f.toString()) ;
        filename = path.getFileName().toString() ;
        this.saveImg();
        this.openImg() ;
    }

    private void saveImg() {
        File inputFile = f;

        // Specify the destination where the new image will be saved
        File outputFile = new File("src\\main\\resources\\media\\"+filename);

        try {
            // Read the image from the file
            BufferedImage image = ImageIO.read(inputFile);

            // Save the image to the new destination with the new name
            boolean result = ImageIO.write(image, "png", outputFile);

            if (result) {
                System.out.println("Image saved successfully!");
            } else {
                System.out.println("Image saving failed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openImg() throws FileNotFoundException {
        Image img = new Image(new FileInputStream("src\\main\\resources\\media\\" + filename)) ;
        photoIV.setImage(img) ;
    }

    public ObservableList<Item> loadinfo() throws IOException {
//        itemObservableArray = db.select_data() ;
        itemObservableArray = excel.select_data() ;
        int total = 0, fruit = 0, food=0, grocery=0, device=0, vegetable=0;
        for (Item i : itemObservableArray) {
            if (Objects.equals(i.getCatagory(), "Fruit")) {
                fruit++;
            }
            else if (Objects.equals(i.getCatagory(), "Food")) {
                food++;
            }
            else if (Objects.equals(i.getCatagory(), "Grocery")) {
                grocery++;
            }
            else if (Objects.equals(i.getCatagory(), "Device")) {
                device++;
            }
            else if (Objects.equals(i.getCatagory(), "Vegetable")) {
                vegetable++;
            }
            total++;
            System.out.println(i);
        }

        itemsTV.setItems(itemObservableArray);
//        for (Item i : itemObservableArray) {
//            itemsTV.getItems().add(i) ;
//        }

        totalL.setText(Integer.toString(total));
        fruitL.setText(Integer.toString(fruit));
        foodL.setText(Integer.toString(food));
        groceryL.setText(Integer.toString(grocery));
        deviceL.setText(Integer.toString(device));
        vegetableL.setText(Integer.toString(vegetable));

        return itemObservableArray ;
    }

    @FXML
    public void filterButtonOnAction(ActionEvent actionEvent) throws IOException {
        ObservableList<Item> filteritemarr = FXCollections.observableArrayList() ;
        ObservableList<Item> finalfilteritemarr = FXCollections.observableArrayList() ;
        String catagory = filtercatagoryCB.getValue() ;
        System.out.println(catagory);
        int from = filterFromSB.getValue() ;
        int to = filterToSB.getValue() ;

        for (Item i : this.loadinfo()) {
            if (Objects.equals(catagory, "All")) {
                filteritemarr = itemObservableArray ;
                break ;
            }
            else if (Objects.equals(i.getCatagory(), catagory)) {
                System.out.println(true);
                filteritemarr.add(i) ;
            }
        }

        for (Item i : filteritemarr) {
            if (from <= i.getPrice() && i.getPrice() <= to) {
                finalfilteritemarr.add(i) ;
            }
        }
        itemsTV.setItems(finalfilteritemarr) ;
    }

    public void editEventListener(String name) {
        nameTF.setText(name);
    }

    public void deleteEventListener(int id) {
        System.out.println("Delete: " + id);
    }
}