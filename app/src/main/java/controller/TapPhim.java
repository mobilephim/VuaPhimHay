package controller;

/**
 * Created by tuandeptrai on 24/11/2016.
 */

public class TapPhim {
    private String name,tentap,link;

    public TapPhim(String name, String tentap, String link) {
        this.name = name;
        this.tentap = tentap;
        this.link = link;
    }

    public TapPhim() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTentap() {
        return tentap;
    }

    public void setTentap(String tentap) {
        this.tentap = tentap;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
