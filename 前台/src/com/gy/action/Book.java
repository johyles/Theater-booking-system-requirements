package com.gy.action;

import com.gy.entity.BookInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {

    public List<BookInfo> bookImport(int screenId) throws SQLException {
        List<BookInfo> fs = new ArrayList<BookInfo>();
        Conn conn = new Conn();
        ResultSet rs = null;
        rs = conn.getBook(screenId);
        while (rs.next()) {
            BookInfo bk = new BookInfo();
            bk.setId(rs.getInt(1));
            bk.setScreenId(rs.getInt(2));
            bk.setSeatX(rs.getInt(3));
            bk.setSeatY(rs.getInt(4));
            bk.setPhone(rs.getString(5));
            bk.setValidCode(rs.getString(6));
            bk.setStatus(rs.getInt(7));
            fs.add(bk);
        }
        return fs;
    }

    public int maxId()
    {
        Conn conn = new Conn();
        int i = conn.getMaxBookId();
        return i;
    }

    public void exportData(List<BookInfo> list)
    {
        int id,screenId,seatX,seatY,status;
        String phone,validCode;
        Conn conn = new Conn();
        for(int i=0;i<list.size();i++)
        {
            id = list.get(i).getId();
            screenId = list.get(i).getScreenId();
            seatX = list.get(i).getSeatX();
            seatY = list.get(i).getSeatY();
            phone = list.get(i).getPhone();
            validCode = list.get(i).getValidCode();
            status = list.get(i).getStatus();
            boolean flag = conn.BookInsert(id,screenId,seatX,seatY,phone,validCode,status);
            if(flag=true)
            {
                System.out.println("购买成功");
            }else
            {
                System.out.println("购买失败");
            }
        }
    }
}
