import java.io.FileNotFoundException;
import java.sql.*;
public class DBtest {

	public static void main(String[] args) {
		try{
			
		
		DBMethods db = new DBMethods();
				  db.readDatabase();
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
