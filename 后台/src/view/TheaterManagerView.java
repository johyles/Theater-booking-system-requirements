package view;

import action.Conn;
import action.TheaterAction;
import entity.Theater;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class TheaterManagerView {
    Conn cc=new Conn();
    ResultSet rs=null;
    JTable table=null;
    DefaultTableModel dtm=null;
    JScrollPane jsp=null;


    //JTableDefineTest jTableDefineTest=null;
    List list;
    JButton buttonper=null;
    JButton buttonnext=null;
    JButton buttonadd=null;
    JButton buttondel=null;
    private int currentPage=1;
    private  int pageSize=3;
    private int lastPage;

    public int getLastPage() {
        int count=cc.AllFilmScreencount();
        if(count%getPageSize()==0)
        {
            lastPage= count/getPageSize();
        }else{
            lastPage= (int) Math.ceil(count/getPageSize())+1;
        }
        return lastPage; }

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

    //主要面板
    public JPanel jpbJpannel() throws SQLException {

        JPanel panel = new JPanel();
        panel.setSize(600, 600);
        panel.setLayout(null);

        String[] columnNames = {"编号","名称","行","列"};
        dtm=new DefaultTableModel(columnNames, 0);

        table=new JTable(dtm);
        jsp = new JScrollPane();
        jsp.setViewportView(table);

        //setTitle("表格");
        jsp.setBounds(20,0,640,500);
        showTable(currentPage);
        panel.add(jsp);

        ImageIcon icon1=new ImageIcon(TheaterManagerView.class.getClassLoader().getResource("img/pre.png"));
        buttonper = new JButton("上一页");
        buttonper.setBounds(70,510,90, 30);
        buttonper.addActionListener(new MyTable());
        icon1.setImage(icon1.getImage().getScaledInstance(100,30,Image.SCALE_DEFAULT));
        buttonper.setIcon(icon1);
        panel.add(buttonper);

        ImageIcon icon2=new ImageIcon(TheaterManagerView.class.getClassLoader().getResource("img/next.png"));
        buttonnext = new JButton("下一页");
        buttonnext.setBounds(200,510,90, 30);
        buttonnext.addActionListener(new MyTable());
        icon2.setImage(icon2.getImage().getScaledInstance(100,30,Image.SCALE_DEFAULT));
        buttonnext.setIcon(icon2);
        panel.add(buttonnext);

        ImageIcon icon3=new ImageIcon(TheaterManagerView.class.getClassLoader().getResource("img/addBtn.png"));
        buttonadd = new JButton("添加");
        buttonadd.setBounds(330,510,90, 30);
        buttonadd.addActionListener(new MyTable());
        icon3.setImage(icon3.getImage().getScaledInstance(100,30,Image.SCALE_DEFAULT));
        buttonadd.setIcon(icon3);
        panel.add(buttonadd);

        ImageIcon icon4=new ImageIcon(TheaterManagerView.class.getClassLoader().getResource("img/delBtn.png"));
        buttondel = new JButton("删除");
        buttondel.setBounds(460,510,90, 30);
        buttondel.addActionListener(new MyTable());
        icon4.setImage(icon4.getImage().getScaledInstance(100,30,Image.SCALE_DEFAULT));
        buttondel.setIcon(icon4);
        panel.add(buttondel);

        return panel;

    }

    public void showTable(int currentPage) throws SQLException {

        while(dtm.getRowCount()>0){
            dtm.removeRow(dtm.getRowCount()-1);
        }
        setCurrentPage(currentPage);
        TheaterAction tt=new TheaterAction();
        list= tt.returnList(currentPage);
        for(int row = 0;row<list.size();row++)    //获得数据
        {
            Vector rowV = new Vector();
            Theater user= (Theater) list.get(row);
            rowV.add(user.getId());
            rowV.add(user.getName());
            rowV.add(user.getRows());
            rowV.add(user.getColumns());
            dtm.addRow(rowV);
        }
        //	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //关闭表格列的自动调整功能。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //单选
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setRowHeight(30);
        dtm.fireTableDataChanged();
    }

    public DefaultTableModel ReturnTable(DefaultTableModel  dtm) throws SQLException {
        while(dtm.getRowCount()>0){
            dtm.removeRow(dtm.getRowCount()-1);
        }
        int count=cc.AllFilmScreencount();
        if(count%getPageSize()==0)
        {
            setCurrentPage(getLastPage()+1);
            return dtm;
        }else{
            setCurrentPage(getLastPage());
        }

        TheaterAction tt=new TheaterAction();
        list= tt.returnList(getCurrentPage());
        for(int row = 0;row<list.size();row++)    //获得数据
        {
            Vector rowV = new Vector();
            Theater user= (Theater) list.get(row);
            rowV.add(user.getId());
            rowV.add(user.getName());
            rowV.add(user.getRows());
            rowV.add(user.getColumns());
            dtm.addRow(rowV);
        }
        //	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //关闭表格列的自动调整功能。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //单选
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setRowHeight(30);
        //dtm.fireTableDataChanged();
        return dtm;
    }


    class MyTable  implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            Conn cc=new Conn();
            ResultSet rs = null;
            if(e.getActionCommand().equals("添加")){
                TheaterAddView ff=new TheaterAddView();
                DefaultTableModel dtm1= null;
                try {
                    dtm1 = ReturnTable(dtm);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                ff.TheaterAddJpanel(dtm1);

            }

            if(e.getActionCommand().equals("删除")) {
                int row = table.getSelectedRow(); //这句选择要删除的行
                //int row1=(getCurrentPage()-1)*pageSize+row;
                if (row != -1)//这句判断是否有选中的行
                {
                    int id = (int) table.getValueAt(row,0);
                    rs = cc.selectTheaterId(id);
                    try {
                        if (!rs.next()) {
                            cc.delTheaterInfo(id);
                            //TheaterTanchuang("删除成功!");
                            dtm.removeRow(row); //这句删除指定行
                            JOptionPane.showMessageDialog(null, "删除成功!","成功",1);
                        } else {
                            //TheaterTanchuang("此放映厅正在使用中，删除失败!");
                            JOptionPane.showMessageDialog(null,"此放映厅正在使用中，删除失败!","失败",0);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    dtm.fireTableDataChanged();
                }
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


        }
    }

}

