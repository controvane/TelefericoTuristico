package com.univalle.javiermurguia.proyectotelefericoturistico2.Models;

import java.io.Serializable;

public class Linea implements Serializable {

    private boolean enable;
    private String mark;
    private String name;
    private int timesVisited;
    private String uuid;
    private String video;

    public Linea(boolean enable, String mark, String name, int timesVisited, String uuid, String video) {
        this.enable = enable;
        this.mark = mark;
        this.name = name;
        this.timesVisited = timesVisited;
        this.uuid = uuid;
        this.video = video;
    }

    public Linea() {
        this.enable = false;
        this.mark = "";
        this.name = "";
        this.timesVisited = -1;
        this.uuid = "";
        this.video = "";
    }

    public boolean isEnable() {
        return enable;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public int getTimesVisited() {
        return timesVisited;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVideo() {
        return video;
    }
}
