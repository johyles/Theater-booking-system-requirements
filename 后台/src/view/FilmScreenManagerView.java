package view;

import action.Conn;
import action.FilmAction;
import entity.FilmScreen;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

public class FilmScreenManagerView extends JFrame{
    Conn cc=new Conn();
    ResultSet rs=null;
    JTable table=null;
    DefaultTableModel dtm=null;
    JScrollPane jsp=null;
    JButton button1=null;
    JButton button2=null;
    JButton button=null;
    JButton button3=null;
    int filmID;
    FilmManagerView fm =new FilmManagerView();
    FilmScreenAddView fs = new FilmScreenAddView();
    private int currentPage=1;
    private  int pageSize=3;
    private int lastPage;


    private File showFileOpenDialog() {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件)
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel(*.xls)", "xls"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            System.out.println("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
        return null;
    }

    public int getLastPage() {
        int count=cc.getCount();
        double hh=count/getPageSize();
        lastPage= (int) Math.ceil(hh)+1;
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

    public JPanel findScreensByFilmId(int filmid) throws SQLException {
        filmID = filmid;
        JPanel panel = new JPanel();
        panel.setSize(700, 600);
        panel.setLayout(null);
        String[] columnNames = {"编号","场次","时间","放映厅"};
        dtm=new DefaultTableModel(columnNames, 0);

        table=new JTable(dtm);
        jsp = new JScrollPane();
        jsp.setViewportView(table);

        //Title("表格");
        jsp.setBounds(20,0,650,500);
        showScreenTable(currentPage,filmID);

        panel.add(jsp);

        button = new JButton("上一页");
        ImageIcon icon = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/pre.png"));
        icon.setImage(icon.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button.setIcon(icon);
        button.setBounds(70,650,90, 30);
        button.addActionListener(new MyTable());
        panel.add(button);

        button1 = new JButton("下一页");
        ImageIcon icon1 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/next.png"));
        icon1.setImage(icon1.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button1.setIcon(icon1);
        button1.setBounds(170,650,90, 30);
        button1.addActionListener(new MyTable());
        panel.add(button1);

        button2 = new JButton("添加");
        ImageIcon icon2 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/addBtn.png"));
        icon2.setImage(icon2.getImage().getScaledInstance(135, 30,Image.SCALE_DEFAULT));
        button2.setIcon(icon2);
        button2.setBounds(270,650,120, 30);
        button2.addActionListener(new addscreen());
        panel.add(button2);

        button3 = new JButton("删除");
        ImageIcon icon3 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/delBtn.png"));
        icon3.setImage(icon3.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button3.setIcon(icon3);
        button3.setBounds(410,650,90, 30);
        button3.addActionListener(new deletescreen());
        panel.add(button3);

        return panel;
    }

    public void showScreenTable(int currentPage,int filmid) throws SQLException {
        java.util.List list;
        while(dtm.getRowCount()>0){
            dtm.removeRow(dtm.getRowCount()-1);
        }
        setCurrentPage(currentPage);
        FilmAction bb=new FilmAction();
        list= bb.returnFilmscreenList(currentPage,filmid);

        for(int row = 0;row<list.size();row++)    //获得数据
        {
            Vector rowV = new Vector();
            FilmScreen user1= (FilmScreen) list.get(row);
            rowV.add(user1.getId());
            rowV.add(user1.getTime());
            rowV.add(user1.getStartDay());
            rowV.add(user1.getName());//数据

            dtm.addRow(rowV);
        }
        //	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //关闭表格列的自动调整功能。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //单选
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setRowHeight(30);
    }

    class MyTable  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("上一页")){
                if(getCurrentPage()<=1){
                    setCurrentPage(1);
                    try {
                        showScreenTable(getCurrentPage(),filmID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    try {
                        showScreenTable(getCurrentPage()-1,filmID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                dtm.fireTableDataChanged();
            }

            if(e.getActionCommand().equals("下一页")){
                if(getCurrentPage()<getLastPage()){
                    try {
                        showScreenTable(getCurrentPage()+1,filmID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    try {
                        showScreenTable(getLastPage(),filmID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                dtm.fireTableDataChanged();
            }
        }
    }

    class deletescreen implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
                int row= table.getSelectedRow(); //这句选择要删除的行
                if(row!=-1)//这句判断是否有选中的行
                {
                    int id = (int)table.getValueAt(row, 0);
                    if(cc.deletescreen(id)==true)
                        System.out.println("删除成功！");
                        dtm.removeRow(row);//这句删除指定行
                        JOptionPane.showMessageDialog(null, "删除成功","成功",1);
                }
                else{
                    System.out.println("删除失败！！");
                }
            dtm.fireTableDataChanged();
        }
    }

    class addscreen implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
            JFrame jf = null;
            try {
                jf = fs.showFilmScreenAddView(filmID,dtm);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jf.setLocationRelativeTo(null);
            jf.setVisible(true);
            dtm.fireTableDataChanged();
        }
    }
}
