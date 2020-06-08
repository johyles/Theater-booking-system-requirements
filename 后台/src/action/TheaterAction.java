package action;

import entity.Theater;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterAction {
    List<Theater> list = new ArrayList<Theater>();
    Conn cc=new Conn();
    public  List<Theater> returnList(int intpage) throws SQLException {

        ResultSet rs=null;
        rs=cc.nowTheaterUser(intpage,3);
        //int count=cc.AllCount();//获取总记录数
        list.clear();
        while(rs.next()){
            Theater  theater=new Theater(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4));
            list.add(theater);
        }
        return list;
    }
}
