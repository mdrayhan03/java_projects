package mainpkg.cart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DataBase {
    private String url ;
    private String username ;
    private String password ;
    private Connection connection ;

    public DataBase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.url = "jdbc:mysql://localhost:3306/dataentry";
        this.username = "root";
        this.password = "442003";
        this.connection = DriverManager.getConnection(this.url, this.username, this.password) ;
    }

    public void createTable(){
        if (connection != null) {
            Statement statement = null ;
            try {
                // Create a Statement object
                statement = connection.createStatement();

                // SQL query to create a table
                String createTableSQL = "CREATE TABLE IF NOT EXISTS fruit_item_tb ("
                        + "id INT NOT NULL AUTO_INCREMENT, "
                        + "name VARCHAR(50) NOT NULL, "
                        + "description VARCHAR(100) , "
                        + "catagory VARCHAR(100) NOT NULL, "
                        + "unit VARCHAR(10) NOT NULL, "
                        + "color VARCHAR(16) NOT NULL, "
                        + "path VARCHAR(50), "
                        + "price INT NOT NULL, "
                        + "total INT NOT NULL, "
                        + "status INT DEFAULT 1, "
                        + "PRIMARY KEY (id))";

                // Execute the query
                statement.execute(createTableSQL);

                System.out.println("Table 'users' is created!");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the Statement and Connection objects
                try {
                    if (statement != null) statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void delete_table() {
        if (connection != null) {
            Statement statement = null ;
            try {
                // Create a Statement object
                statement = connection.createStatement();

                // SQL query to create a table
                String createTableSQL = "DROP TABLE fruit_item_tb";

                // Execute the query
                statement.execute(createTableSQL);

                System.out.println("Table 'users' is created!");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the Statement and Connection objects
                try {
                    if (statement != null) statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insert_data(String name, String description, String catagory, String unit, String color, String path, int price, int total) {
        if (connection != null) {
            PreparedStatement preparedStatement = null ;
            try {
                String insertSQL = "INSERT INTO fruit_item_tb (name, description, catagory, unit, color, path, price, total) VALUES (?,?,?,?,?,?,?,?)" ;

                // Execute the query
                preparedStatement = connection.prepareStatement(insertSQL);

                preparedStatement.setString(1, name) ;
                preparedStatement.setString(2, description) ;
                preparedStatement.setString(3, catagory) ;
                preparedStatement.setString(4, unit) ;
                preparedStatement.setString(5, color) ;
                preparedStatement.setString(6, path) ;
                preparedStatement.setInt(7, price);
                preparedStatement.setInt(8, total);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted.");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the Statement and Connection objects
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ObservableList<Item> select_data() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList() ;
        if (connection != null) {
            PreparedStatement preparedStatement = null ;
            try {
                String selectSQL = "SELECT id, name, description, catagory, unit, color, path, price, total FROM fruit_item_tb WHERE status = 1" ;


                preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet resultSet = preparedStatement.executeQuery() ;

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String catagory = resultSet.getString("catagory");
                    String unit = resultSet.getString("unit");
                    String color = resultSet.getString("color");
                    String path = resultSet.getString("path");
                    int price = resultSet.getInt("price");
                    int total = resultSet.getInt("total");

                    itemObservableList.add(new Item(id ,name, description, catagory, unit, color, path, price, total)) ;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the Statement and Connection objects
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return  itemObservableList ;
    }

    public void delete_data(int id) {
        if (connection != null) {
            Statement statement = null ;
            try {

                // Create a statement object to perform queries
                statement = connection.createStatement();

                // SQL DELETE query
                String sql = "DELETE FROM fruit_item_tb WHERE id = "+id;  // Replace with your condition

                // Execute the DELETE query
                int rowsAffected = statement.executeUpdate(sql);

                // Check how many rows were deleted
                if (rowsAffected > 0) {
                    System.out.println("Record deleted successfully!");
                } else {
                    System.out.println("No record found with the specified condition.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the Statement and Connection objects
                try {
                    if (statement != null) statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
