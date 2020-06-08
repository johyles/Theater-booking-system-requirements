package action;

import java.sql.*;
import java.util.List;

public class Conn {
    Connection conn = null;
    Statement st = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //数据库连接
    public void getConn() {
        //1.加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2.建立与数据库的连接
        String url = "jdbc:mysql://localhost:3306/test";
        try {
            conn = DriverManager.getConnection(url, "root", "abc123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭
    public void closeConn() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (pst != null) {
                pst.close();
                pst = null;
            }
            if (st != null) {
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

    //通过id查询电影
    public ResultSet getFilm(int id) {
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from movie where id=" + id;
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //查询所有电影
    public ResultSet Movie(){
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from movie Order By time Desc";
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    //电影总记录数
    public int getCount() {
        getConn();
        int count1 = 0;
        try {
            st = conn.createStatement();
            String sql = "select count(*) from movie";
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count1;
    }

    //电影分页
    public ResultSet nowPageUser(int nowPage,int pageSize){
        getConn();
        try{
            String sql = "select * from movie Order By time Desc limit ?,? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, (nowPage-1)*pageSize);
            pst.setInt(2, pageSize);
            rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //电影删除
    boolean delFlag = true;
    public boolean deleteMovie(int uid){
        getConn();
        try{
            st = conn.createStatement();
            String sql = "delete from movie where uid="+uid;
            st.executeUpdate(sql);
        }catch(Exception e){
            delFlag = false;
            e.printStackTrace();
        }
        return delFlag;
    }

    //电影增加数据
    boolean addFlag = true;
    public boolean addMovie(List list){
        getConn();
        try{
            for (int i = 0; i < list.size(); i++) {
                List temp = (List) list.get(i);
                for (int j = 0; j < temp.size(); j++) {
                String sql = "insert into movie (uname,info,photo,time) values (?,?,?,?) ";
                pst = conn.prepareStatement(sql);
                pst.setString(1, (String) temp.get(0));
                pst.setString(2, (String) temp.get(1));
                pst.setString(3, (String) temp.get(2));
                pst.setString(4, (String) temp.get(3));
            pst.executeUpdate();
                }
            }
        }catch(Exception e){
            addFlag = false;
            e.printStackTrace();
        }
        return addFlag;
    }


    //场次分页
    public ResultSet nowPageUserscreen(int nowPage,int pageSize,int filmid){
        getConn();
        try{
            String sql = "select * from filmscreen  where filmid="+filmid+" limit ?,?";
            int qq= (nowPage-1)*pageSize;
            pst=conn.prepareStatement(sql);
            pst.setInt(1, qq);
            pst.setInt(2, pageSize);
            rs=pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //场次总记录数
    public int getScreenCount() {
        getConn();
        int count1 = 0;
        try {
            st = conn.createStatement();
            String sql = "select count(*) from filmscreen";
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count1;
    }

    //场次展示
    public ResultSet getFilmScreen(int filmid) {
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from filmScreen ";
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //场次增加数据
    boolean addFlag1 = true;
    public boolean addScreen(int filmid,String startDay,String time,int theaterId,String name){
        getConn();
        try{
            String sql = "insert into  filmscreen (filmid,startDay,time,theaterId,name)values(?,?,?,?,?)  ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,filmid);
            pst.setString(2,startDay);
            pst.setString(3,time);
            pst.setInt(4,theaterId);
            pst.setString(5,name);
            pst.executeUpdate();
        }catch(Exception e){
            addFlag1 = false;
            e.printStackTrace();
        }
        return addFlag1;
    }


    //场次删除
    boolean delFlag1 = true;
    public boolean deletescreen(int id){
        getConn();
        try{
            st = conn.createStatement();
            String sql = "delete from filmscreen where id="+id;
            st.executeUpdate(sql);
        }catch(Exception e){
            delFlag1 = false;
            e.printStackTrace();
        }
        return delFlag1;
    }

    //放映厅总记录数
    public int AllFilmScreencount(){
        getConn();
        int count=0;
        try{
            st = conn.createStatement();
            String sql = "select count(*) from theater";
            rs = st.executeQuery(sql);//查询
            rs.next();
            count=rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //放映厅当前页的记录数
    public ResultSet nowTheaterUser(int nowPage,int pageSize){
        getConn();
        try{
            String sql = "select * from theater limit ?,?";
            int qq= (nowPage-1)*pageSize;
            pst=conn.prepareStatement(sql);
            pst.setInt(1, qq);
            pst.setInt(2, pageSize);
            rs=pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //删除放映室信息
    public void delTheaterInfo(int id){//-------------------------------------删除
        getConn();
        try{
            String sql = "delete from theater where id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("数据删除成功！\n");
            closeConn();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据删除失败！\n");
        }
    }

    //查询要删除的放映厅的id是否和book中有没有相应的
    public ResultSet selectTheaterId(int id){//----------------查询需要删除的信息从而选择
        getConn();
        try {
            String sql = "select * from bookinfo where id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            rs=pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //添加放映室信息
    public void addTheaterInfo(int rows,int columns,String name){//----------------------增加
        getConn();
        try{
            String sql = "insert into theater(rows,colums,name) VALUES (?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,rows) ;
            pst.setInt(2,columns);
            pst.setString(3,name);
            pst.executeUpdate();
            System.out.println("数据插入成功！\n");
            closeConn();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据插入失败！\n");
        }
    }

    //查询放映厅的名字是否重复
    public ResultSet selectTheaterName(String name){//----------------查询需要添加的信息从而选择
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from theater where name='"+name+"'";
            rs = st.executeQuery(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //放映厅展示
    public ResultSet getTheater() {
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from theater";
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //通过在book中的screenid查询到theater中的name
    public ResultSet IdtTheaterName(int screenid){//----------------查询需要添加的信息从而选择
        getConn();
        try {
            String sql = "select name from theater where id=(select theaterId from filmscreen where id=?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, screenid);
            rs=pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //通过在book中的screenid查询到film中的name
    public ResultSet IdtFilmName(int screenid){//----------------查询需要添加的信息从而选择
        getConn();
        try {
            String sql = "select uname from movie where uid=(select filmId from filmscreen where id = ? )";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, screenid);
            rs=pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //通过在book中的screenid查询到film中的name
    public ResultSet IdtFilmTime(int screenid){//----------------查询需要添加的信息从而选择
        getConn();
        try {
            String sql = "select startDay,time from filmscreen where id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, screenid);
            rs=pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //取票后更新status=1
    public void updateStatus(String phone,String validcode){//-----------修改
        getConn();
        try{
            String sql = "update bookinfo set status=1 where phone =? and vaildCode=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,phone);
            pst.setString(2,validcode);
            pst.executeUpdate();
            System.out.println("数据更新成功！\n");
            closeConn();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据修改失败！\n");
        }
    }
    //放映厅总记录数
    public int MaxCount(){
        getConn();
        int count=0;
        try{
            st = conn.createStatement();
            String sql = "select max(id) from theater";
            rs = st.executeQuery(sql);//查询
            rs.next();
            count=rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //预定总记录数
    public int AllCount(){
        getConn();
        int count=0;
        try{
            st = conn.createStatement();
            String sql = "select count(*) from bookinfo";
            rs = st.executeQuery(sql);//查询
            rs.next();
            count=rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }

    //预定
    public ResultSet getBook() {
        getConn();
        try {
            st = conn.createStatement();
            String sql = "select * from bookinfo";
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //预定记录数
    public int getBookCount() {
        getConn();
        int count1 = 0;
        try {
            st = conn.createStatement();
            String sql = "select count(*) from bookinfo";
            rs = st.executeQuery(sql);
            rs.next();
            count1 = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count1;
    }

    //预定分页
    public ResultSet nowPageUserbook(int nowPage,int pageSize){
        getConn();
        try{
            String sql = "select * from bookinfo limit ?,?";
            int qq= (nowPage-1)*pageSize;
            pst=conn.prepareStatement(sql);
            pst.setInt(1, qq);
            pst.setInt(2, pageSize);
            rs=pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //预定添加
    boolean isFlag1 = false;
    public boolean BookInsert(int id, Integer screenId, Integer seatX, Integer seatY,      //插入预定信息
                              String phone, String validCode){
        getConn();
        int nextId;
        try{
            st = conn.createStatement();
            String sql = "select max(id) from bookinfo";
            rs = st.executeQuery(sql);
            if(rs.next()){
                nextId = rs.getInt(1)+1;
                String sql2 = "insert into bookinfo (id,screenId,seatX,seatY,phone,validCode) values (?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql2);
                pst.setInt(1,nextId);
                pst.setInt(2,screenId);
                pst.setInt(3,seatX);
                pst.setInt(4,seatY);
                pst.setString(5,phone);
                pst.setString(6,validCode);
                pst.executeUpdate();
                isFlag1=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isFlag1;
    }

    //取件过程
    public ResultSet pointticket(String phone,String code){
        getConn();
        try{
            st=conn.createStatement();
            String sql="select * from bookinfo where phone='"+phone+"' and vaildCode='"+code+"'";
            rs=st.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }




}