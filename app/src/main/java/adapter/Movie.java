package adapter;

/**
 * Created by Neeraj Sain on 7/20/2016.
 */
public class Movie {
    private String title, genre, year,types;
    int icon;

    public Movie() {
    }

    public Movie(String title, String genre, String year, int icon, String type) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.icon = icon;
        this.types = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getType() {

        return types;
    }

    public void setType(String type) {
        this.types = type;
    }

    public int geticon() {

        return icon;
    }

    public void seticon(int icon) {
        this.icon = icon;
    }

}
