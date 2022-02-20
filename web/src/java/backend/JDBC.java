package backend;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user1
 */
public class JDBC {
    private final String user = "isaboutt_user";
    private final String password="e)cOI3b_Q*#]";
    private final String url = "jdbc:mysql://localhost:3306/isabout_isabout";
    private static Connection connect;
    private static Statement stmt;
    public static String status;
    Driver driver ;
    public Connection connect(){
        try {
            driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connect = DriverManager.getConnection(url, user, password);
            status = "database connection successful";
        } catch (Exception ex) {
            status = "Unable to connect to database";
        }
        
        return connect;
    }
   
    public Statement statement(Connection connect){
        try{
            stmt = connect.createStatement();
            status = "database connection successful";
        }catch(Exception e){
            status = "Unable to connect to database";
        }
        
        return stmt;
    }
    
    public Statement update(Statement stmt, String sql){
        try{
            if(stmt.executeUpdate(sql) == 1){
                status = "database update executed successfully";
            }
            
        }catch(Exception e){
            status = e.getMessage();
        }
        
        return stmt;
    }
    
    public ResultSet query(Statement stmt, String sql){
        try {
            ResultSet rs = stmt.executeQuery(sql);
            status = "database query excuted successfully";
            return rs;
        }catch(Exception ex) {
           status = "Unable to execute database query";
           return null;
        }
        
    }
    public void close(Connection connect, Statement stmt){
        try{
            connect.close();
            stmt.close();
        }catch(Exception e){
        }
        
    }
    
    public void close(Connection connect, Statement stmt, ResultSet rs){
        try{
            connect.close();
            stmt.close();
            rs.close();
        }catch(Exception e){
        }
        
    }
    
    public int getRowSize(String tableName){
        int count = 0;
        try {
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt, "SELECT COUNT(*) FROM "+tableName);
            if(rs.next()){
                count = rs.getInt("COUNT(*)");
            }
            jdbc.close(connect, stmt, rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return count;
    }
    
    public int getRowSizeByQuery(String sql){
        int count = 0;
        try {
            JDBC jdbc = new JDBC();
            Connection connect = jdbc.connect();
            Statement stmt = jdbc.statement(connect);
            ResultSet rs = jdbc.query(stmt,sql );
            if(rs.next()){
                count = rs.getInt("COUNT(*)");
            }
            jdbc.close(connect, stmt, rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return count;
    }
    
    
}
