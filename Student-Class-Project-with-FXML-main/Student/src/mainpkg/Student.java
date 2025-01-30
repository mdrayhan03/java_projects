package mainpkg;

public class Student {
    int id ;
    String name ;
    float cgpa ;
    
    Student (){
        this.id = 0 ;
        this.name = "TBA" ;
        this.cgpa = 0.00f ;
    }
    
    Student (int id , String name , float cgpa) {
        this.id = id ;
        this.name = name ;
        this.cgpa = cgpa ;
    }
    
    public String show_object () {
        return "ID : " + id + "\tName : " + name + "\tCGPA : " + cgpa ;
    }
}
