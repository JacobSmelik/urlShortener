package smelik.urlShortener;

import java.sql.Connection;
import java.util.Random;
import smelik.urlShortener.Objects.ShortenedUrl;


public class urlShorteningSerivce {
    
    private static final String CONST_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    
    public static String generateShort(String longUrl){
        
        Random random = new Random();
        String code = "";
        
        Connection con = DB.connect();
        if(con == null){
            return null;
        }
        
        while(true){
            for(int i = 0; i < 7; i++){
                int randomIndex = random.nextInt(CONST_ALPHABET.length() - 1);
                code += CONST_ALPHABET.charAt(randomIndex);   
            }
            
            if(DB.selectCode(con, code) == null){
                DB.insert(con, new ShortenedUrl(code, longUrl));
                DB.close(con);
                return code;
            }
        }   
    }
    
}
