package controller;

/**
 * Created by Hoàng Thông on 11/11/2016.
 */

public class FilmMaster {
    String name, thumb, link, type, year, decs;

    public FilmMaster() {
    }

    public FilmMaster(String name, String thumb, String link, String type, String year, String decs) {
        this.name = name;
        this.thumb = thumb;
        this.link = link;
        this.type = type;
        this.year = year;
        this.decs = decs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }
}
