package mainpkg;

public class Student extends User{
    private String major ;
    private float cgpa ;

    public Student() {
    }

    public Student(String major, float cgpa, int id, String name, String gender, String password) {
        super(id, name, gender, password);
        this.major = major;
        this.cgpa = cgpa;
    }

    public String getMajor() {
        return major;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() + "major=" + major + ", cgpa=" + cgpa + '}';
    } 
}
