package view;

import action.BookInfoAction;
import action.Conn;
import entity.BookInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class GetTicketView {

    Conn cc=new Conn();
    ResultSet rs=null;
    JPanel jpd=new JPanel();
    int isflag=3;
    List list1;
    String phone=null;
    String code=null;
    String phoneNo;
    String validCode;
    JTextField text1 = new JTextField();
    JTextField text2 = new JTextField();
    JLabel label=new JLabel("");
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public JPanel getTicket() throws SQLException {

        jpd.setSize(600,600);
        jpd.setLayout(null);
        //jpd.setBounds(0,0,600,600);


        JLabel phone=new JLabel("手机号:");
        phone.setBounds(200, 150, 50, 30);

        JLabel code=new JLabel("取票码:");
        code.setBounds(200, 220, 50, 30);

        label.setBounds(240,250,200,20);

        ImageIcon icon=new ImageIcon(GetTicketView.class.getClassLoader().getResource("img/submitBtn.png"));
        JButton choose=new JButton("选定");
        choose.setBounds(240, 300, 90, 30);
        icon.setImage(icon.getImage().getScaledInstance(110,30,Image.SCALE_DEFAULT));
        choose.setIcon(icon);

        choose.addActionListener(new MyTable());

        text1.setBounds(250, 150, 120, 30);
        text2.setBounds(250,220 , 120, 30);

        jpd.add(label);
        jpd.add(phone);
        jpd.add(code);
        jpd.add(choose);
        jpd.add(text1);
        jpd.add(text2);

        return jpd;
    }

    public void getTicketMessage(String phone,String code) throws SQLException {

        JDialog ff=new JDialog();
        //ff.setSize(300, 500);
        ff.setBounds(550,250,300,500);
        BookInfoAction bb=new BookInfoAction();

        JPanel jp = new JPanel();
        jp.setSize(300, 500);
        jp.setLayout(null);

        list1=bb.getticket(phone,code);
        for(int row = 0;row<list1.size();row++)    //获得数据
        {
            BookInfo user= (BookInfo) list1.get(row);

            JOptionPane.showMessageDialog(null,"<html>电影票序号:"+user.getId()+"<br>手机号:"+user.getPhone()+"<br>取票码:"+user.getValidCode()+"<br>座位号:"+user.getSeatY()+"行"+user.getSeatX()+"列","取票成功",1);


        }
    }

    public int ValidExist(String phone,String code) throws SQLException {
        isflag =3;
        BookInfoAction bb=new BookInfoAction();
        ResultSet rs1=null;
        list1=bb.getticket(phone,code);
        if(list1.size()>0 ){
            for(int row = 0;row<list1.size();row++)    //获得数据
            {
                Vector rowV = new Vector();
                BookInfo user= (BookInfo) list1.get(row);

                if(user.getStatus()==1){
                    isflag =2;//已出票
                }
                else {
                    isflag =1;//可以打印
                }
            }
        }else{
            isflag=3;//信息不对
        }
        return isflag;
    }

    class MyTable  implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            try {
                phoneNo=text1.getText().trim();
                validCode=text2.getText().trim();
                if(ValidExist(phoneNo,validCode)==3){

                    label.setText("输入手机号或取票码有误!");
                    //label22.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
                    label.setForeground(Color.red);//设置颜色
                } else if(ValidExist(phoneNo,validCode)==1){
                    cc.updateStatus(phoneNo,validCode);
                    getTicketMessage(phoneNo,validCode);
                    label.setText("取票成功!");
                    //label22.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
                    label.setForeground(Color.red);//设置颜色
                }else if(ValidExist(phoneNo,validCode)==2){
                    label.setText("已出票，无法取票!");
                    //label22.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
                    label.setForeground(Color.red);//设置颜色
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
