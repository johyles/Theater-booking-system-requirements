package com.gy.action;

import com.gy.entity.FilmScreen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class FilmScreenInfo {

    public List<FilmScreen> screenImport() throws SQLException {
        List<FilmScreen> list = new ArrayList<FilmScreen>();
        //FilmScreen fs = new FilmScreen();
        Conn conn = new Conn();
        ResultSet rs = conn.getFilmScreen();
        while (rs.next()) {
            FilmScreen fs = new FilmScreen();
            fs.setId(rs.getInt(1));
            fs.setFilmId(rs.getInt(2));
            fs.setStartDay(rs.getDate(3));
            fs.setTime(rs.getString(4));
            fs.setTheaterId(rs.getInt(5));
            fs.setName(rs.getString(6));
            list.add(fs);
        }
        System.out.println(list.get(2).getTheaterId());

        return list;
    }

    public List<FilmScreen> screenByFilmId(int filmId) throws SQLException {
        List<FilmScreen> list = new ArrayList<FilmScreen>();
        Conn conn = new Conn();
        ResultSet rs = conn.getByFilmId(filmId);
        int i = 0;
        while (rs.next()) {
            FilmScreen fs = new FilmScreen();
            fs.setId(rs.getInt(1));
            fs.setFilmId(rs.getInt(2));
            fs.setStartDay(rs.getDate(3));
            fs.setTime(rs.getString(4));
            fs.setTheaterId(rs.getInt(5));
            fs.setName(rs.getString(6));
            list.add(fs);
        }
        return list;
    }

    public List<FilmScreen> startDayD(List<FilmScreen> list)
    {
        List<FilmScreen> list1 = new ArrayList<FilmScreen>();
        for(int i=0;i<list.size();i++)
        {
            int flag=0;
            for(int j=0;j<i;j++)
            {
                Date mm = list.get(i).getStartDay();
                Date mm1 = list.get(j).getStartDay();
                if(mm.getTime()==mm1.getTime())
                {
                    flag=1;
                }
            }
            if(flag==0)
            {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

    public int selectByScreenId(int screenId) throws SQLException {
        Conn conn = new Conn();
        int theaterId = 0;
        ResultSet rs = conn.getByScreenId(screenId);
        if(rs.next())
        {
            theaterId = rs.getInt(5);
        }
        return theaterId;
    }

}
