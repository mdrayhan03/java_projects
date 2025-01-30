/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mainpkg;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author cis101
 */
public class StudentFxmlController implements Initializable {

    @FXML
    private TextField getIDTextField;
    @FXML
    private TextField getNameTextField;
    @FXML
    private TextField getCgpaTextField;
    @FXML
    private Button addNewStudentObjectButton;
    @FXML
    private Button showStudentObjectButton;
    @FXML
    private Label showOutputLabel;
    @FXML
    private Label wrongIDLable;
    @FXML
    private Label wrongCgpaLable;
    @FXML
    private Label studentCountLabel;
    ArrayList<Student> list = new ArrayList<Student> () ;
    int counter = 0 ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void createANewStudentObjectButton(ActionEvent event) {
        int flag = 0 ;
        String id , name ;
        float cgpa ;
        id = getIDTextField.getText() ;
        name = getNameTextField.getText() ;
        cgpa = Float.parseFloat(getCgpaTextField.getText()) ;
        if (id.length() != 7){
            wrongIDLable.setText("Wrong ID");
            flag = 1 ;
        }
        if (cgpa < 0.00f || cgpa > 4.01f ) {
            wrongCgpaLable.setText("Wrong CGPA") ;
            flag = 1 ;
        }
        if (flag == 1) {
            showOutputLabel.setText("Wrong Information. Sorry unable to create Student Object.");
            addNewStudentObjectButton.setStyle("-fx-background-color: red ;") ;
        }
        else if (flag == 0) {            
            list.add(new Student(Integer.parseInt(id) , name , cgpa)) ;
            showOutputLabel.setText("");
            addNewStudentObjectButton.setStyle("-fx-background-color: gray ;") ;
            showStudentObjectButton.setStyle("-fx-background-color: gray ;");
            counter++ ;
            studentCountLabel.setText(Integer.toString(counter));
            getIDTextField.clear() ;
            getNameTextField.clear() ;
            getCgpaTextField.clear() ;
            wrongIDLable.setText("");
            wrongCgpaLable.setText("") ;
        }
    }

    @FXML
    private void showAllStudentObjectButton(ActionEvent event) {
        String str = "" ;
        showOutputLabel.setText("Sorry, No object to show.");
        System.out.println(str.length()) ;
//        if (str.length() == 0){
//        }
        for (Student ele : list) {
            str += ele.show_object() + "\n" ;
        }
        showOutputLabel.setText(str);
        showStudentObjectButton.setStyle("-fx-background-color: blue ;");
    }
    
}
