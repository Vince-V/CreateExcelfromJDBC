import java.sql.DriverManager;
import java.sql.Statement;

public class SingletonConnect {
    
    private static Statement stmt = null;

    final static String USERNAME = "user";
    final static String PASSWORD = "pwd";

    private static String URL = "jdbc:mysql://localhost/mysql";

    private static SingletonConnect sc = new SingletonConnect();

    
    private SingletonConnect() {
        
    }
    
    public static SingletonConnect dbConnection() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            stmt = DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();
            
        } catch(Exception e){
            e.printStackTrace();
        }
        return sc;
    }
    
}
