package view;


import action.BookInfoAction;
import action.Conn;
import entity.BookInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class BookInfoManagerView {
    Conn cc=new Conn();
    ResultSet rs=null;
    ResultSet rs1=null;
    ResultSet rs2=null;
    ResultSet rs3=null;
    JTable table=null;
    DefaultTableModel dtm=null;
    JScrollPane jsp=null;
    //JTableDefineTest jTableDefineTest=null;
    List list;
    List list1;
    List list2;
    JButton button1=null;
    JButton button2=null;
    JButton button=null;
    JButton button3=null;
    private int currentPage=1;
    private  int pageSize=3;
    private int lastPage;

    public int getLastPage() {
        int count=cc.AllCount();
        if(count%getPageSize()==0)
        {
            lastPage= count/getPageSize();
        }else{
            lastPage= (int) Math.ceil(count/getPageSize())+1;
        }
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JPanel jpcJpannel() throws SQLException {

        JPanel panel = new JPanel();
        panel.setSize(700, 600);
        panel.setLayout(null);

        String[] columnNames = {"电影","放映时间","放映厅","座位","状态"};
        dtm=new DefaultTableModel(columnNames, 0);

        table=new JTable(dtm);
        jsp = new JScrollPane();
        jsp.setViewportView(table);

        //setTitle("表格");
        jsp.setBounds(20,0,650,500);
        showTable(currentPage);
        panel.add(jsp);

        button = new JButton("首页");
        button.setBackground(Color.pink);
        button.setBounds(70,510,90, 30);
        button.addActionListener(new MyTable());
        //button.setActionCommand("首页");
        panel.add(button);

        button1 = new JButton("上一页");
        ImageIcon icon = new ImageIcon(BookInfoManagerView.class.getClassLoader().getResource("img/pre.png"));
        icon.setImage(icon.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button1.setIcon(icon);
        button1.setBounds(200,510,90, 30);
        button1.addActionListener(new MyTable());
        panel.add(button1);

        button2 = new JButton("下一页");
        ImageIcon icon1 = new ImageIcon(BookInfoManagerView.class.getClassLoader().getResource("img/next.png"));
        icon1.setImage(icon1.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button2.setIcon(icon1);
        button2.setBounds(330,510,90, 30);
        button2.addActionListener(new MyTable());
        panel.add(button2);

        button3 = new JButton("末页");
        button3.setBackground(Color.pink);
        button3.setBounds(460,510,90, 30);
        button3.addActionListener(new MyTable());
        panel.add(button3);

        return panel;

    }

    public void showTable(int currentPage) throws SQLException {

        while(dtm.getRowCount()>0){
            dtm.removeRow(dtm.getRowCount()-1);
        }
        setCurrentPage(currentPage);
        BookInfoAction bb=new BookInfoAction();
        list= bb.returnList(currentPage);
        for(int row = 0;row<list.size();row++)    //获得数据
        {
            Vector rowV = new Vector();
            BookInfo user= (BookInfo) list.get(row);

            rs1=cc.IdtFilmName(user.getScreenId());
            if(rs1.next())
            {
                rowV.add(rs1.getString(1));
            }else{
                rowV.add("");
            }

            rs2=cc.IdtFilmTime(user.getScreenId());
            if(rs2.next())
            {
                rowV.add(rs2.getDate(1)+" "+rs2.getString(2));
            }else{
                rowV.add("");
            }

            rs3=cc.IdtTheaterName(user.getScreenId());
            if(rs3.next())
            {
                rowV.add(rs3.getString(1));
            }else{
                rowV.add("");
            }

            rowV.add(user.getSeatX()+"行"+user.getSeatY()+"列");

            if(user.getStatus()==0)
            {
                rowV.add("未出票");
            }else {
                rowV.add("已取票");
            }
            //rowV.add(user.getValidCode());

            dtm.addRow(rowV);
        }
        //	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //关闭表格列的自动调整功能。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //单选
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setRowHeight(30);
    }

    class MyTable  implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("首页")){
                try {
                    showTable(1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dtm.fireTableDataChanged();
            }
            if(e.getActionCommand().equals("上一页")){
                if(getCurrentPage()<=1){
                    setCurrentPage(1);
                    try {
                        showTable(getCurrentPage());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    try {
                        showTable(getCurrentPage()-1);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                dtm.fireTableDataChanged();
            }

            if(e.getActionCommand().equals("下一页")){
                if(getCurrentPage()<getLastPage()){
                    try {
                        showTable(getCurrentPage()+1);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    try {
                        showTable(getLastPage());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                dtm.fireTableDataChanged();
            }

            if(e.getActionCommand().equals("末页")){
                try {
                    showTable(getLastPage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dtm.fireTableDataChanged();
            }
        }
    }


}
