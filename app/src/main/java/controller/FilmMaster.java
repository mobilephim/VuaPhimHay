package controller;

/**
 * Created by Hoàng Thông on 11/11/2016.
 */

public class FilmMaster {
    String name, thumb, link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public FilmMaster(String name, String thumb, String link) {
        this.name = name;
        this.thumb = thumb;
        this.link = link;

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

}
