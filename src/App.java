import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class App {
    public static void main(String[] args) throws Exception {
        createTable();
        insertion();
        get();
    }
    public static void createTable()throws Exception{
        try{
            Connection conne=getConnection();
            PreparedStatement create = conne.prepareStatement("CREATE TABLE IF NOT EXISTS myfilestructuredatabase(nodeid int, nodetype varchar(255), nodename varchar(255), parentnodeid int, PRIMARY KEY(nodeid))");
            create.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        finally{
            System.out.println("FINALLY ACHIEVED CREATING THE TABLE");}
    }
    public static void insertion() throws Exception{
        try{
            Connection conne=getConnection();
            PreparedStatement insert = conne.prepareStatement("INSERT INTO myfilestructuredatabase(nodeid, nodetype, nodename, parentnodeid) VALUES (1,'R','C:',NULL),(2,'D','Documents',1),(3,'D','Images',2),(4,'F','Image1.jpg',3),(5,'F','Image2.jpg',3),(6,'F','Image3.jpg',3),(7,'D','Works',2),(8,'D','Accountant',7),(9,'F','Accounting.xls',8),(10,'F','AnnualReport.xls',8),(11,'F','Letter.doc',7),(12,'D','Program Files',1),(13,'D','Skype',12),(14,'F','Skype.exe',13),(15,'F','Readme.txt',13),(16,'D','Mysql',12),(17,'F','Mysql.exe',16),(18,'F','Mysql.com',16)");
            insert.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            System.out.println("THE DATA HAS BEEN INSERTED");
        }
    }
    public static ArrayList<String> get()throws Exception{
        try{
            Connection con=getConnection();
            PreparedStatement pathfind =con.prepareStatement("SELECT nodename FROM filelisting ORDER BY nodename"+"WITH filelisting as"+"SELECT nodeid, nodename"+"FROM myfilestructuredatabase"+"WHERE parentnodeid IS NULL"+"UNION ALL"+"SELECT myfilestructuredatabase.nodeid, filelisting.nodename+'\'+ myfilestructuredatabase.nodename as [nodename]"+"FROM filelisting"+"INNER JOIN myfilestructuredatabase ON myfilestructuredatabase.parentnodeid = filelisting.nodeid");
            ResultSet result=pathfind.executeQuery();
            ArrayList<String> array=new ArrayList<String>();
            while(result.next()){
                System.out.println(result.getString("nodename"));
            }
            System.out.println("The path will be displayed.....");
            return array;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public static Connection getConnection()throws Exception{
        try{
            String driver="com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/samplefiledatabase";
            String user = "root";
            String password = "";
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,password);
            System.out.println("ESTABLISHED THE CONNECTION");
            return con;
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
}

