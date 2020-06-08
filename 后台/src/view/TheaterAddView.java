package view;

import action.Conn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TheaterAddView {
    Object[] message=new Object[4];
    public Object[] getMessage() {
        return message;
    }

    public void setMessage(Object[] message) {
        this.message = message;
    }

    public   void  TheaterAddJpanel(DefaultTableModel dtm){
        // 创建 JFrame 实例
        JFrame frame = new JFrame();
        JPanel jpa=new JPanel();    //创建面板
        jpa.setLayout(null);
        frame.add(jpa);
        frame.setSize(400,450);
        frame.setLocationRelativeTo(null);
        // 输入放映厅名称的文本域
        JLabel label1=new JLabel("放映厅名称：");
        label1.setBounds(50,50,100,30);
        JTextField TheaterName = new JTextField();
        TheaterName.setBounds(160,50,160,30);
        jpa.add(TheaterName);
        // 输入总排数的文本域
        JLabel label2=new JLabel("总排数：");
        label2.setBounds(50,100,100,30);
        JTextField TheaterRows = new JTextField();
        TheaterRows.setBounds(160,100,165,30);
        jpa.add(TheaterRows);
        // 输入总列数的文本域
        JLabel label3=new JLabel("总列数：");
        label3.setBounds(50,150,100,30);
        JTextField TheaterColumns = new JTextField();
        TheaterColumns.setBounds(160,150,165,30);
        jpa.add(TheaterColumns);

        JLabel label22=new JLabel("");
        label22.setBounds(160,80,300,20);

        jpa.add(label1);//将标签添加到面板
        jpa.add(TheaterName);
        jpa.add(label2);
        jpa.add(TheaterRows);
        jpa.add(label22);
        jpa.add(label3);
        jpa.add(TheaterColumns);
        //添加至面板
        //创建“添加”按钮
        ImageIcon icon3=new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/addBtn.png"));
        JButton AddButton = new JButton("添加");
        AddButton.setBounds(140, 270, 80, 30);
        icon3.setImage(icon3.getImage().getScaledInstance(100,30,Image.SCALE_DEFAULT));
        AddButton.setIcon(icon3);
        jpa.add(AddButton);

        //按钮监听
        AddButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Conn cc=new Conn();
                try {
                    if(ValidAdd(TheaterName.getText())){
                        int i=Integer.parseInt(TheaterRows.getText());
                        int j=Integer.parseInt(TheaterColumns.getText());
                        int row=cc.MaxCount();
                        message[0]=row;
                        message[1]=TheaterName.getText();
                        message[2]=i;
                        message[3]=j;
                        //setMessage(message);
                        dtm.addRow(message);
                        dtm.fireTableDataChanged();

                        cc.addTheaterInfo(i,j,TheaterName.getText());

                        JOptionPane.showMessageDialog(null, "插入成功!","成功",1);
                        frame.dispose();
                    }
                    else{
                        label22.setText("此放映厅已存在!");
                        //label22.setFont(new Font("楷体",Font.BOLD,16));    //修改字体样式
                        label22.setForeground(Color.red);//设置颜色
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); // 设置界面可见
    }

    public static boolean ValidAdd(String name) throws SQLException {
        boolean convertSuccess=false;
        Conn cc=new Conn();
        ResultSet rs=null;
        rs=cc.selectTheaterName(name);
        if(!rs.next()){
            convertSuccess=true;
        }
        return convertSuccess;
    }

}
