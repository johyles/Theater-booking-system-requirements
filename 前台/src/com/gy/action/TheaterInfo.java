package com.gy.action;

import com.gy.entity.Theater;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterInfo {

    public Theater theaterImport(int theaterId) throws SQLException {
        Theater tr = new Theater();
        Conn conn = new Conn();
        ResultSet rs = conn.getTheater(theaterId);
        while(rs.next())
        {
            tr.setId(rs.getInt(1));
            tr.setName(rs.getString(2));
            tr.setRows(rs.getInt(3));
            tr.setColumns(rs.getInt(4));
        }
        return tr;
    }
}
