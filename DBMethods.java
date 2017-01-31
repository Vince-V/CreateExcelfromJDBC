import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DBMethods {
	
	private final String URL = "jdbc:mysql://localhost/mysql";
	private final String USERNAME = "root";
	private final String PASSWORD = "*****";
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet getOwner = null;
	 //Blank workbook
	private XSSFWorkbook workbook = new XSSFWorkbook(); 
     
    //Create a blank sheet
	private XSSFSheet sheet = workbook.createSheet("Employee Data");
	
	private FileOutputStream out;

	public DBMethods() throws ClassNotFoundException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		    System.out.println("Connection established");
            if (conn != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());    
            }

		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
	} // end constructor
	
	public void readDatabase() throws FileNotFoundException{	
		try
		{
		
			statement = conn.createStatement();
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("made it to createStatement in readDatabase");
			
			getOwner= statement.executeQuery("select * from properties");
			ResultSetMetaData ownersMetaData = getOwner.getMetaData();
			int ownerColumns = ownersMetaData.getColumnCount();
			System.out.println( "OWNERS Table of the property database:" );

			int rownum = 1;
			int columnNameRownum = 0;
			while(getOwner.next())
			{

			System.out.println(" ");

				int cellnum = 0;
				Row row = sheet.createRow(rownum++);
				Row columnNameRow = sheet.createRow(columnNameRownum);
				for(int i=1;i<=ownerColumns;i++)
	            {
				   int j = i-1;
				   int k = i-1;
				   Cell cell = columnNameRow.createCell(j++);
				   cell.setCellValue(ownersMetaData.getColumnName( i ));
	
	               cell = row.createCell(k++);

	               if(getOwner.getObject(i) instanceof Integer){
	                    cell.setCellValue((Integer)getOwner.getObject(i));
	               }else if(getOwner.getObject(i) instanceof Double){
	                    cell.setCellValue((Double)getOwner.getObject(i));
	               }else if(getOwner.getObject(i) instanceof Long){
	                    cell.setCellValue((Long)getOwner.getObject(i));
	               }else if(getOwner.getObject(i) instanceof Float){
	                    cell.setCellValue((Float)getOwner.getObject(i));
	               }else
	                    cell.setCellValue((String)getOwner.getObject(i).toString());
	                    System.out.printf("%-9s\t",getOwner.getObject(i).toString());
	            }
			} // end while
			out = new FileOutputStream(new File("excel_fromQuery.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("excels written successfully on disk.");
		  }	catch(SQLException sqle)
			{
				sqle.printStackTrace();
			}catch(IOException io)
			{
				io.printStackTrace();
			}
		    finally {
				closeDatabase();
			}
		} // end DBMethods			
	 	
	public void closeDatabase()
	{
		try
		{
		   conn.close();
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
}
