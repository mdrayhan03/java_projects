package mainpkg;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MidExamSceneController implements Initializable {

    @FXML    private Label payableAmountLabel;
    @FXML    private ComboBox<String> selectProductComboBox;
    @FXML    private ComboBox<Integer> quantityComboBox;
    @FXML    private TextField unitPriceBDTTextField;
    @FXML    private TextField predefinedVatTextField;
    @FXML    private TableView<CartItem> showCartTableView;
    @FXML    private TableColumn<CartItem, String> productNameTableColumn;
    @FXML    private TableColumn<CartItem, Float> unitPriceTableColumn;
    @FXML    private TableColumn<CartItem, Integer> quantityTableColumn;
    @FXML    private TableColumn<CartItem, Integer> vatTableColumn;
    @FXML    private TableColumn<CartItem, Float> vatAmountTableColumn;
    @FXML    private TableColumn<CartItem, Float> totalAmountTableColumn;
    @FXML    private Label showTotalVatLabel;
    @FXML    private TextField maximumProductPriceTextField;
    @FXML    private ComboBox<Integer> selectVatComboBox;
    ArrayList<CartItem> cartList ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cartList = new ArrayList<>() ;
        
        selectProductComboBox.getItems().addAll("Arong Milk" , "Nescafe Gold 100gm" , "Rui Fish kg pack" , "Lux Soap White 100gm" , "Life Buye Soap White 100gm" , "Dettol Soap White 100gm" , "Ilish Fish kg pack" , "Chicken kg pack" , "Mutton kg pack" , "Koi Fish kg pack") ;
        quantityComboBox.getItems().addAll(1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9) ;
        selectVatComboBox.getItems().addAll(1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9) ;
        
        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vatTableColumn.setCellValueFactory(new PropertyValueFactory<>("vatRate"));
        vatAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("vatAmount"));
        totalAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        
    }    
    
    @FXML
    private void setPriceVatOnAction(ActionEvent event) {
        int vat = 0 , price = 0 ;
        String productName = selectProductComboBox.getValue() ;
        if (null != productName) switch (productName) {
            case "Arong Milk":
                vat = 5 ;
                price = 100 ;
                break;
            case "Nescafe Gold 100gm":
                vat = 5 ;
                price = 900 ;
                break;
            case "Rui Fish kg pack":
                vat = 3 ;
                price = 400 ;
                break;
            case "Lux Soap White 100gm":
                vat = 5 ;
                price = 80 ;
                break;
            case "Life Buye Soap White 100gm":
                vat = 5 ;
                price = 100 ;
                break;
            case "Dettol Soap White 100gm":
                vat = 5 ;
                price = 120 ;
                break;
            case "Ilish Fish kg pack":
                vat = 3 ;
                price = 900 ;
                break;
            case "Chicken kg pack":
                vat = 4 ;
                price = 300 ;
                break;
            case "Mutton kg pack":
                vat = 5 ;
                price = 800 ;
                break;
            case "Koi Fish kg pack":
                vat = 3 ;
                price = 400 ;
                break;
            default:
                break;
        }
        unitPriceBDTTextField.setText(Integer.toString(price)) ;
        predefinedVatTextField.setText(Integer.toString(vat)) ;
    }

    @FXML
    private void addProductToCartButtonOnClick(ActionEvent event) {
        int flag = 0 ;
        String productName = selectProductComboBox.getValue() ;
        float unitPrice = Float.parseFloat(unitPriceBDTTextField.getText()) ;
        int quantity = quantityComboBox.getValue() ;
        int vatRate = Integer.parseInt(predefinedVatTextField.getText()) ;
        for (CartItem cart : cartList) {
            if (cart.getProductName() == productName) {
                cart.setQuantity(quantity);
                flag = 1 ;
            }
        }
        if (flag == 0) {
            cartList.add(new CartItem(productName, unitPrice , vatRate , quantity)) ;
        }
        selectProductComboBox.getSelectionModel().clearSelection() ;
        unitPriceBDTTextField.clear() ;
        predefinedVatTextField.clear() ; 
        quantityComboBox.getSelectionModel().clearSelection() ;
    }

    @FXML
    private void checkOutAndShowBillButtonOnClick(ActionEvent event) {
        float totalpayableAmount = 0 ;
        for (CartItem cart: cartList){
            totalpayableAmount += cart.getTotalAmount() ;
            showCartTableView.getItems().add(cart) ;
        }
        String str = "After checkout, TOTAL amount payable is " +totalpayableAmount+"tk and the cart detail is:" ;
        payableAmountLabel.setText(str) ;
    }

    @FXML
    private void showTotalVatForQualifiedProductsButtonOnClick(ActionEvent event) {
        int maxTotalAmount = 0 ;
        int maxPrice = Integer.parseInt(maximumProductPriceTextField.getText()) ;
        int maxVat = selectVatComboBox.getValue() ;
        for (CartItem cart: cartList) {
            if (cart.getUnitPrice() <= maxPrice && cart.getVatRate() == maxVat){
                maxTotalAmount += cart.getVatAmount() ;
            }
        }
        String str = "The total vat amount for the product in cart having unit price <= "+maxPrice+" with "+maxVat+"% vat is: "+maxTotalAmount ;
        showTotalVatLabel.setText(str) ;
        maximumProductPriceTextField.clear() ;
        selectVatComboBox.getSelectionModel().clearSelection() ;
    }
    
}
