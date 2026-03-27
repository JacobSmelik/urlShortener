package smelik.urlShortener;

import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import smelik.urlShortener.Objects.ShortenedUrl;


public class DB {
    public static Connection connect(){
        try {
            Connection connection = getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
            if(connection != null){
                return connection;
            }
            System.err.println("Database couldn't connect");
            return null;
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    public static void close(Connection con){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean insert(Connection con, ShortenedUrl values){
        
        var sql = "INSERT INTO urlshortened "
                + "VALUES(?, ?)";       
        try {
            var stmt = con.prepareStatement(sql);
            stmt.setString(1, values.getShortUrl());
            stmt.setString(2, values.getLongUrl());
            int insertedRow = stmt.executeUpdate();
            if(insertedRow != 0){
                return true;  
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.err.println("Insert failed");
        return false;
    }
    
    public static ShortenedUrl selectCode(Connection con, String code){
        var sql = "SELECT * FROM urlshortened "
                + "WHERE short = ?";
        
        try {
            var stmt = con.prepareCall(sql);
            stmt.setString(1, code);
            var result = stmt.executeQuery();
            
            if(result.next()){
                
                return new ShortenedUrl(result.getString(1), result.getString(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
