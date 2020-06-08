package view;

import action.Conn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
//import org.apache.commons.lang3.time.DateUtils;

public class FilmScreenAddView extends JFrame{
    DefaultTableModel dtm=null;
    int getFilmid;
    public JFrame showFilmScreenAddView(int filmid,DefaultTableModel dtm1) throws ParseException {
        dtm = dtm1;
        getFilmid = filmid;
        // 创建 JFrame 实例
        JFrame frame = new JFrame("空场次添加 ");
        JPanel jpa=new JPanel();    //创建面板
        jpa.setLayout(null);

        frame.add(jpa);
        // Setting the width and height of frame
        frame.setSize(400, 450);

        /*
         * 创建文本域用于用户输入
         */
        // 输入场次名称的文本域
        JLabel label1=new JLabel("场次名称：");
        label1.setBounds(50,50,100,30);
        JTextField FilmName = new JTextField();
        FilmName.setBounds(160,50,160,30);
        jpa.add(FilmName);
        // 输入场次日期的文本域
        JLabel label2=new JLabel("场次日期：");
        label2.setBounds(50,100,100,30);
        JTextField FilmDate = new JTextField();
        FilmDate.setBounds(160,100,165,30);
        jpa.add(FilmDate);
        // 输入场次时间的文本域
        JLabel label3=new JLabel("场次时间：");
        label3.setBounds(50,150,100,30);
        JTextField FilmTime = new JTextField();
        FilmTime.setBounds(160,150,165,30);
        jpa.add(FilmTime);
        //放映室下拉列表
        JLabel label4=new JLabel("放映室：");    //创建标签
        label4.setBounds(50,200,100,30);
        JComboBox FilmRoom=new JComboBox();    //创建JComboBox

        FilmRoom.setBounds(160,200,165,30);
        FilmRoom.addItem("name0");    //向下拉列表中添加一项
        FilmRoom.addItem("VIP1");
        FilmRoom.addItem("VIP2");
        FilmRoom.addItem("VIP3");
        JLabel label22=new JLabel("");
        label22.setBounds(160,125,300,20);
        JLabel label33=new JLabel("");
        label33.setBounds(160,175,300,20);
        jpa.add(label1);//将标签添加到面板
        jpa.add(FilmName);
        jpa.add(label2);
        jpa.add(FilmDate);
        jpa.add(label22);
        jpa.add(label3);
        jpa.add(FilmTime);
        jpa.add(label33);
        jpa.add(label4);
        jpa.add(FilmRoom);//添加至面板



        //创建“添加”按钮
        JButton AddButton = new JButton("添加");
        AddButton.setBounds(140, 270, 80, 30);
        jpa.add(AddButton);


        Conn cc = new Conn();

        //按钮监听
        AddButton.addActionListener(new ActionListener()
        {
            int clicks=0;
            public void actionPerformed(ActionEvent e)
            {
                if(!ValidDate(FilmDate.getText())){
                    label22.setText("请使用yyyy-MM-dd格式");
                    //label22.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
                    label22.setForeground(Color.red);//设置颜色
                }
                if(!ValidTime(FilmTime.getText())){
                    label33.setText("请使用HH:mm:ss格式");
                    label33.setForeground(Color.red);//设置颜色
                }
                else if(ValidTime(FilmTime.getText())){
                    label33.setText("格式正确");
                    label33.setForeground(Color.blue);//设置颜色
                }
                if (FilmName.getText()!=null&&FilmDate.getText()!=null&&FilmTime.getText()!=null&&FilmRoom.getSelectedIndex()!=0){
                    if(cc.addScreen(getFilmid,FilmDate.getText(),(String)FilmTime.getText(),Integer.parseInt(FilmName.getText()),(String)FilmRoom.getSelectedItem())==true){
                        System.out.println("添加成功！");
                        Vector rowV = new Vector();
                        rowV.add(getFilmid);
                        rowV.add(FilmDate.getText());
                        rowV.add((String)FilmTime.getText());
                        rowV.add(Integer.parseInt(FilmName.getText()));
                        rowV.add((String)FilmRoom.getSelectedItem());
                        dtm.addRow(rowV);
                        JOptionPane.showMessageDialog(null, "添加成功","成功",1);
                    }else{
                        System.out.println("添加失败！");
                    }
                }
            }
        });
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;// 设置界面可见
    }

    public static boolean ValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    public static boolean ValidTime(String str) {
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }


}
