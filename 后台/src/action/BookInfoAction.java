package action;


import entity.BookInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookInfoAction {

    List<BookInfo> list = new ArrayList<BookInfo>();
    List<BookInfo> list1=new ArrayList<BookInfo>();
    Conn cc=new Conn();
    public  List<BookInfo> returnList(int intpage) throws SQLException {

        ResultSet rs=null;
        rs=cc.nowPageUserbook(intpage,3);
        //int count=cc.AllCount();//获取总记录数
        list.clear();
        while(rs.next()){
            BookInfo  bookInfo=new BookInfo(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                    rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7));
            list.add(bookInfo);

        }
        return list;
    }

    public  List<BookInfo> getticket(String phone,String code) throws SQLException {
        ResultSet rs1=null;
        rs1=cc.pointticket(phone,code);
        list1.clear();
        while(rs1.next()){
            BookInfo user=new BookInfo(rs1.getInt(1), rs1.getInt(2), rs1.getInt(3),rs1.getInt(4),
                    rs1.getString(5),rs1.getString(6),rs1.getInt(7));
            list1.add(user);
        }
        return list1;
    }

}

