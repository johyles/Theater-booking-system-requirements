package com.gy.entity;

import java.util.Date;

public class FilmScreen {
    private Integer id;
    private Integer filmId;
    private Date startDay;
    private String time;
    private Integer theaterId;
    private String name;

    public FilmScreen() {
    }

    public FilmScreen(Integer id, Integer filmId, Date startDay, String time,
                      Integer theaterId, String name) {
        super();
        this.id = id;
        this.filmId = filmId;
        this.startDay = startDay;
        this.time = time;
        this.theaterId = theaterId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getFilmId() {
        return filmId;
    }
    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }
    public Date getStartDay() {
        return startDay;
    }
    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Integer getTheaterId() {
        return theaterId;
    }
    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

}
