package mainpkg;

public class User {
    protected int id ;
    protected String name , gender , password ;
    protected static String uniName = "IUB" ;

    public User() {
    }

    public User(int id, String name, String gender, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public static String getUniName() {
        return uniName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void setUniName(String uniName) {
        User.uniName = uniName;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", gender=" + gender + ", password=" + password ;
    }
}
