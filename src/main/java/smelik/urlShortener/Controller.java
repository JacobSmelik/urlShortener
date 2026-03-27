package smelik.urlShortener;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import smelik.urlShortener.Objects.ShortenedUrl;

@RestController
public class Controller {
    
    public static boolean isUrlValid(String url){
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
    
    @PostMapping("/shorten")
    public static ResponseEntity shorten(String longUrl, HttpServletRequest req){
        if(!isUrlValid(longUrl)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("URL not valid.");
        }
        
        String shortCode = urlShorteningSerivce.generateShort(longUrl);
        if(shortCode == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Internal database error");
        }
        
        return ResponseEntity.ok(req.getScheme() + "://" + req.getServerName()
                                + ":" + req.getServerPort() + "/" + shortCode);
    }
    
    @GetMapping("/{code}")
    public static ResponseEntity redirect(@PathVariable String code, HttpServletResponse res){
        Connection con = DB.connect();
        if(con == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Internal database error");
        }
        ShortenedUrl urlObject = DB.selectCode(con, code);
        DB.close(con);
        
        if (urlObject != null){
            try {
                res.sendRedirect(urlObject.getLongUrl());
                return ResponseEntity.ok(null);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Code doesn't exist");
    }
}
