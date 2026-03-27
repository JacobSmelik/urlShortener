# URL shortener exercise

This is a simple exerceise, creating backend part of URL shortener using Spring, Java and PostgreSQL.

## Database table used

CREATE TABLE UrlShortened(
    Short varchar(7) PRIMARY KEY,
    LongUrl text NOT NULL
);
