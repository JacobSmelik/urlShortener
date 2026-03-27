package smelik.urlShortener.Objects;


public class ShortenedUrl {
    String shortUrl;
    String longUrl;

    public ShortenedUrl(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
    
    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
