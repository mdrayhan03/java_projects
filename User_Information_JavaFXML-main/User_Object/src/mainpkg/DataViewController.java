package mainpkg;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataViewController implements Initializable {

    @FXML    private TextField idTextField;
    @FXML    private TextField nameTextField;
    @FXML    private TextField cgpaTextField;
    @FXML    private RadioButton maleRadioButton;
    @FXML    private RadioButton femaleRadioButton;
    @FXML    private RadioButton othersRadioButton;
    @FXML    private PasswordField pwPasswordField;
    @FXML    private ComboBox<String> majorComBox;
    @FXML    private TableView<Student> studentInfoTableView;
    @FXML    private TableColumn<Student, Integer> idTableColumn;
    @FXML    private TableColumn<Student, String> nameTableColumn;
    @FXML    private TableColumn<Student, String> genderTableColumn;
    @FXML    private TableColumn<Student, String> majorTableColumn;
    @FXML    private TableColumn<Student, Float> cgpaTableColumn;
    @FXML    private TableColumn<Student, String> uniNameTableColumn;
    ArrayList<Student> studList  ;
    ToggleGroup tg ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        studList = new ArrayList<Student>() ;
        
        tg = new ToggleGroup() ;
        maleRadioButton.setToggleGroup(tg);
        femaleRadioButton.setToggleGroup(tg);
        othersRadioButton.setToggleGroup(tg);
        
        majorComBox.getItems().addAll("CSE","EEE","PS","MIS","Finance");
        
        idTableColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        cgpaTableColumn.setCellValueFactory(new PropertyValueFactory<Student,Float>("cgpa"));
        genderTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("gender"));
        majorTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("major"));
        uniNameTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("uniName"));
    }    

    @FXML
    private void addNewStudentButtonMouseOnAction(ActionEvent event) {
        int id = Integer.parseInt(idTextField.getText()) ;
        String name = nameTextField.getText() ;
        String gender = "" ;
        if (maleRadioButton.isSelected()) {gender = "Male" ;}
        else if (femaleRadioButton.isSelected()) {gender = "Female" ;}
        else if (othersRadioButton.isSelected()) {gender = "Others" ;}
        String password = pwPasswordField.getText() ;
        String major = majorComBox.getValue();
        float cgpa = Float.parseFloat(cgpaTextField.getText()) ;
        
        studList.add(new Student(major , cgpa , id , name , gender , password)) ;
        
        idTextField.clear();      nameTextField.clear();
        cgpaTextField.clear();    pwPasswordField.clear();
    }

    @FXML
    private void showStudentInfoButtonMouseOnAction(ActionEvent event) {
        for (Student s : studList) {
        studentInfoTableView.getItems().add(s) ;
        }
    }
    
}
