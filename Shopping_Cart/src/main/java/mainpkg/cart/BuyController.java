package mainpkg.cart;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class BuyController
{
    @javafx.fxml.FXML
    private TableColumn<CartItem, Integer> priceTC;
    @javafx.fxml.FXML
    private TableColumn<CartItem, String> nameTC;
    @javafx.fxml.FXML
    private TableColumn<CartItem, String> unitTC;
    @javafx.fxml.FXML
    private TableColumn<CartItem, String> catagoryTC;
    @javafx.fxml.FXML
    private TableView<CartItem> listTV;
    @javafx.fxml.FXML
    private TableColumn<CartItem, CheckBox> checkTC;
    @javafx.fxml.FXML
    private TableColumn<CartItem, Integer> totalPriceTC;
    @javafx.fxml.FXML
    private TableColumn<CartItem, Integer> totalUnitTC;
    @javafx.fxml.FXML
    private Label totalPriceL;

    ObservableList<CartItem> cartItemObservableList ;
    ObservableList<CartItem> selectedCartList = FXCollections.observableArrayList() ;
    @javafx.fxml.FXML
    private TextField nameTF;
    @javafx.fxml.FXML
    private TextField pnTF;
    @javafx.fxml.FXML
    private DatePicker dateDP;
    @javafx.fxml.FXML
    private TextField emailTF;

    public void setter (ObservableList<CartItem> cartItemObservableList) {
        this.cartItemObservableList = cartItemObservableList ;
        listTV.setItems(cartItemObservableList);
    }

    @javafx.fxml.FXML
    public void initialize() {
        checkTC.setCellValueFactory(new PropertyValueFactory<>("check")) ;
        nameTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getName()));
        unitTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getUnit()));
        priceTC.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getItem().getPrice()).asObject());
        catagoryTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getCatagory()));
        totalUnitTC.setCellValueFactory(new PropertyValueFactory<>("add")) ;
        totalPriceTC.setCellValueFactory(new PropertyValueFactory<>("total")) ;

        dateDP.setValue(LocalDate.now());
    }

    @javafx.fxml.FXML
    public void itemsButtonOnAction(ActionEvent actionEvent) {
        int cost = 0 ;
        for (CartItem cart: cartItemObservableList) {
            if (cart.getCheck().isSelected()) {
                cost += cart.getTotal() ;
                selectedCartList.add(cart) ;
            }
        }
        totalPriceL.setText(cost + "tk");
    }

    @javafx.fxml.FXML
    public void confirmButtonOnAction(ActionEvent actionEvent) throws IOException {
        ExcelControl excel = new ExcelControl() ;
        int total = 0 ;
        for (CartItem cart: selectedCartList) {
            excel.update_data(cart.getItem().getId(), cart.getItem().getTotal()-cart.getAdd()) ;
            total += cart.getTotal() ;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setHeaderText("Shopping confirmation.") ;
        alert.setContentText("Your shopping is successful. For Money Receipt click Money Receipt button.\nYour total bill:"+total+"tk");
        alert.showAndWait() ;
    }

    @javafx.fxml.FXML
    public void receiptOnAction(ActionEvent actionEvent) throws FileNotFoundException {
        PDFControl pdf = new PDFControl(nameTF.getText(), pnTF.getText(), emailTF.getText(), selectedCartList) ;
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setHeaderText("Money Receipt.") ;
        alert.setContentText("Your Money Receipt creation done.");
        alert.showAndWait() ;
    }
}