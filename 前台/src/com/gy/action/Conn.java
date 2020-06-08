package com.gy.action;

import java.io.StringReader;
import java.sql.*;

public class Conn {
    Connection conn = null;
    Statement st = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void getConn(){
        //1.加载驱动
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        //2.建立与数据库的连接
        String url = "jdbc:mysql://localhost:3306/test";
        try{
            conn = DriverManager.getConnection(url, "root", "abc123");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeConn(){
        try {
            if(rs!=null){
                rs.close();
                rs = null;
            }
            if(pst!=null){
                pst.close();
                pst = null;
            }
            if(st!=null){
                st.close();
                st = null;
            }
            conn.close();
            conn = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ResultSet getFilm(int id) {    //查找电影信息
        getConn();
        try{
            st = conn.createStatement();
            String sql = "select * from movie where uid="+id;
            rs = st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int getCount(){      //查找电影数量
        getConn();
        int count1 = 0;
        try{
            st = conn.createStatement();
            String sql = "select count(*) from movie";
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count1;
    }

    public ResultSet getBook(int screenId){    //按照场次id来查找预定信息
        getConn();
        try{
            st = conn.createStatement();
            String sql = "select * from bookinfo where screenId = "+screenId;
            rs = st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    boolean isFlag = false;
    public boolean BookInsert(int id, Integer screenId, Integer seatX, Integer seatY,      //插入预定信息
                              String phone, String validCode,Integer status){
        getConn();
        int nextId;
        try{
            st = conn.createStatement();
            String sql = "select max(id) from bookinfo";
            rs = st.executeQuery(sql);
            if(rs.next()){
                nextId = rs.getInt(1)+1;
                String sql2 = "insert into bookinfo (id,screenId,seatX,seatY,phone,vaildCode,status) values (?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql2);
                pst.setInt(1,nextId);
                pst.setInt(2,screenId);
                pst.setInt(3,seatX);
                pst.setInt(4,seatY);
                pst.setString(5,phone);
                pst.setString(6,validCode);
                pst.setInt(7,status);
                pst.executeUpdate();
                isFlag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isFlag;
    }

    public ResultSet getTheater(int theaterId)        //获取放映厅信息
    {
        getConn();
        try{
            st = conn.createStatement();
            String sql = "select * from theater where id = "+theaterId;
            rs = st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFilmScreen()        //获取场次信息
    {
        getConn();
        try{
            st = conn.createStatement();
            String sql = "select * from filmScreen";
            rs = st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int getScreenCount()
    {
        getConn();
        int count1 = 0;
        try{
            st = conn.createStatement();
            String sql = "select count(*) from filmScreen";
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count1;
    }

    public ResultSet getByFilmId(int FilmId)    //按电影id查找电影
    {
        getConn();
        try{
            st = conn.createStatement();
            String sql = "select * from filmScreen where filmId ="+FilmId;
            rs = st.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int getByFilmCount(int filmId)
    {
        getConn();
        int count1 = 0;
        try{
            st = conn.createStatement();
            String sql = "select count(*) from filmScreen where filmId =" + filmId;
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count1;
    }

    public ResultSet getByScreenId(int screenId)      //按场次信息获取场次信息
    {
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from filmScreen where id = "+screenId;
            rs =st.executeQuery(sql);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public int getMaxBookId()     //查找预定的最大id
    {
        getConn();
        int i = 0;
        try {
            st = conn.createStatement();
            String sql = "select max(id) from bookinfo";
            rs = st.executeQuery(sql);
            rs.next();
            i = rs.getInt(1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return i;
    }
}
