# URL shortener exercise

This is a simple exerceise, creating backend part of URL shortener usin Spring, Java and PostreSQL.

## Database table used

CREATE TABLE UrlShortened(
    Short varchar(7) PRIMARY KEY,
    LongUrl text NOT NULL
);
