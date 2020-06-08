package action;

import entity.Film;
import entity.FilmScreen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FilmAction  {



    private List<Film> film= new ArrayList<>();
    public List<Film> returnFilmList(int intpage) throws SQLException{
        Conn cc=new Conn();
        ResultSet rs;
        rs=cc.nowPageUser(intpage,3);
        film.clear();
        while(rs.next()){
            Film film1 = new Film();
            film1.setId(rs.getInt(1));
            film1.setName(rs.getString(2));
            film1.setInfo(rs.getString(3));
            film1.setPicUrl(rs.getString(4));
            film1.setPublishDate(rs.getDate(5));

            film.add(film1);
        }
        return film;
    }

    private List<FilmScreen> th= new ArrayList<>();
    public List<FilmScreen> returnFilmscreenList(int intpage,int filmid) throws SQLException{
        Conn cc=new Conn();
        ResultSet rs;
        rs=cc.nowPageUserscreen(intpage,3,filmid);
        th.clear();
        while(rs.next()){
            FilmScreen fs = new FilmScreen();
            fs.setId(rs.getInt(1));
            fs.setFilmId(rs.getInt(2));
            fs.setStartDay(rs.getDate(3));
            fs.setTime(rs.getString(4));
            fs.setTheaterId(rs.getInt(5));
            fs.setName(rs.getString(6));

            th.add(fs);
        }
        return th;
    }

}
