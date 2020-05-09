/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.utils;

import ferru97.sketchfab.api.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vito
 */
public class SQLDatabase {
    private Connection con;
    private String DB_host;
    private String usr;
    private String psw;
    
    
    public SQLDatabase(String host, String database,String username, String password, int port){
        DB_host = "jdbc:mysql://"+String.valueOf(host)+":"+String.valueOf(port)+"/"+database+"?serverTimezone=UTC";
        usr = username;
        psw = password;
    }
    
    public boolean connect(){
        try{ 
           Class.forName("com.mysql.cj.jdbc.Driver"); 
           
            // Establishing Connection 
           con = DriverManager.getConnection(DB_host, usr, psw); 

            if (con != null){
                System.out.println("Connected to DB");
                return true;
            }                           
            else{
                 System.out.println("Not Connected to DB");
                 return false;
            }            
            
        } 
        catch(Exception e) 
        { 
            System.out.println("Error:" + e); 
            return false;
        } 
    }
    
    public boolean insertCategory(String cat){
        
        Statement stmt; 
        try {
            if(con.isClosed())
                connect();
            stmt = con.createStatement();
            // Inserting data in database 
            String q1 = "insert into categories values('" +cat+ "')"; 
            int x = stmt.executeUpdate(q1); 
            if (x > 0){
                System.out.println("Catgory Inserted");  
                return true;
            }                            
            else{
                System.out.println("Catgory Insert Failed");
                return false;
            }            
                    
        } catch (SQLException ex) {
            System.out.println("SQL Exception: "+ex.toString());
            return false;
        }
    }
    
    public boolean insertModel(Model m){
        Statement stmt; 
        String sq1 = "";
        try {
            if(con.isClosed())
                connect();
            stmt = con.createStatement();
            // Inserting data in database 
             sq1 = "insert into models values('" +m.getUid()+ "','" +m.getUrl()+ "',"
                    + "'" +m.getName().replace("'", "''")+ "','" +m.getDate()+ "','" +m.getLike_count()+"',"
                    + "'" +m.getView_count()+"','" +m.getComment_count()+"','" +m.getVertex_count()+"',"
                    + "'" +m.getFace_count()+"','" +m.getSound_count()+"')"; 
            int x = stmt.executeUpdate(sq1); 
            if (x > 0){
                //Insert Tags
                ArrayList<String> tags = m.getTags();
                tags.stream().forEach(tag->{
                    String sql2 = "insert into model_tags values(DEFAULT,'"+m.getUid()+"',"
                                  + "'" +tag.replace("'", "''")+ "')"; 
                    try { 
                        int k = stmt.executeUpdate(sql2);
                        if(k<1)
                            System.out.println("Faild to insert tag for UID="+m.getUid());
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: "+ex.toString());
                    }
                    
                });
                
                //Insert categories
                ArrayList<String> categories = m.getCategories();
                categories.stream().forEach(cat->{
                    String sql2 = "insert into model_categories values(DEFAULT,'"+m.getUid()+"',"
                                  + "'" +cat.replace("'", "''")+ "')"; 
                    try { 
                        int k = stmt.executeUpdate(sql2);
                        if(k<1)
                            System.out.println("Faild to insert category for UID="+m.getUid());
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: "+ex.toString());
                    }
                    
                });
                
                
                //Insert comments
                ArrayList<String> comments = m.getComments();
                comments.stream().forEach(comment->{
                    String sql2 = "insert into model_comments values(DEFAULT,'"+m.getUid()+"',"
                                  + "'" +comment.replace("'", "''")+ "')"; 
                    try { 
                        int k = stmt.executeUpdate(sql2);
                        if(k<1)
                            System.out.println("Faild to insert comment for UID="+m.getUid());
                    } catch (SQLException ex) {
                        System.out.println("SQL Exception: "+ex.toString());
                    }
                    
                });
                return true;
            }                            
            else{
                System.out.println("Model Insert Failed");
                return false;
            }            
                    
        } catch (SQLException ex) {
            return false;
        }
    }
    
    
    
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
