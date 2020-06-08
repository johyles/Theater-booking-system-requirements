package com.gy.action;

import com.gy.entity.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmExport {

    public Film Import(int i) throws SQLException {
        Conn conn = new Conn();
        Film film = new Film();
        ResultSet rs = conn.getFilm(i);
        if(rs.next()) {
            film.setId(rs.getInt("uid"));
            film.setName(rs.getString("uname"));
            film.setIntroduce(rs.getString("info"));
            film.setUrl(rs.getString("photo"));
            film.setDate(rs.getDate("time"));
        }

        return film;
    }
}
