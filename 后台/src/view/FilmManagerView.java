package view;

import action.FilmAction;
import action.Conn;
import entity.Film;
import jxl.Sheet;
import jxl.Workbook;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FilmManagerView extends JFrame{
    Conn cc=new Conn();

    ResultSet rs=null;
    JTable table=null;
    CustomModel dtm=null;
    JScrollPane jsp=null;
    private int filmid=1;
    JButton button1=null;
    JButton button2=null;
    JButton button=null;
    JButton button3=null;
    JButton button4=null;
    int row;

    private int currentPage=1;
    private  int pageSize=3;
    private int lastPage;

    public int getFilmid() {
        return filmid;
    }

    public void setFilmid(int filmid1) {
        filmid = filmid1;
    }

    public String showFileOpenDialog() {
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
        File file = fileChooser.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径


            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            System.out.println("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
        return file.getAbsolutePath();
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

    public JPanel findFilmsByPage() throws SQLException {

        Object[] columnNames = {"编号","名称","简介","封面","时间"};

        JPanel panel = new JPanel();
        panel.setSize(700, 600);
        panel.setLayout(null);

        Object[][] tableDate3={{"C:/Users/16090/Desktop/film/双子杀手.jpg","","","",""}};
        dtm = new CustomModel(tableDate3,columnNames);

        //dtm = new CustomModel(columnNames, 0);
        // dtm=new DefaultTableModel(columnNames, 0);


        table=new JTable(dtm);
        jsp = new JScrollPane();
        jsp.setViewportView(table);

        //Title("表格");
        jsp.setBounds(20,0,650,500);
        showTable(currentPage);


        button = new JButton("上一页");
        ImageIcon icon = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/pre.png"));
        icon.setImage(icon.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button.setIcon(icon);
        button.setBounds(70,510,90, 30);
        panel.add(jsp);
        button.addActionListener(new MyTable());
        //button.setActionCommand("首页");
        panel.add(button);

        button1 = new JButton("下一页");
        ImageIcon icon1 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/next.png"));
        icon1.setImage(icon1.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button1.setIcon(icon1);
        button1.setBounds(170,510,90, 30);
        button1.addActionListener(new MyTable());
        panel.add(button1);

        button2 = new JButton("从Excel批量导入");
        ImageIcon icon2 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/addBatch.png"));
        icon2.setImage(icon2.getImage().getScaledInstance(140, 30,Image.SCALE_DEFAULT));
        button2.setIcon(icon2);
        button2.setBounds(270,510,120, 30);
        button2.addActionListener(new dao());
        panel.add(button2);

        button3 = new JButton("删除");
        ImageIcon icon3 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/delBtn.png"));
        icon3.setImage(icon3.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button3.setIcon(icon3);
        button3.setBounds(410,510,90, 30);
        button3.addActionListener(new deleteMovie());
        panel.add(button3);

        button4 = new JButton("场次管理");
        ImageIcon icon4 = new ImageIcon(FilmManagerView.class.getClassLoader().getResource("img/manageScreenBtn.png"));
        icon4.setImage(icon4.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
        button4.setIcon(icon4);
        button4.setBounds(510,510,90, 30);
        button4.addActionListener(new managerMovie());
        panel.add(button4);

        return panel;

    }

    public void showTable(int currentPage) throws SQLException {
        java.util.List list;
        while(dtm.getRowCount()>0){
            dtm.removeRow(dtm.getRowCount()-1);
        }
        setCurrentPage(currentPage);
        FilmAction bb=new FilmAction();
        list= bb.returnFilmList(currentPage);
        for(int row = 0;row<list.size();row++)    //获得数据
        {   JLabel but=null;
            Vector rowV = new Vector();
            Film user= (Film) list.get(row);
            rowV.add(user.getId());
            rowV.add(user.getName());
            rowV.add(user.getInfo());
            //rowV.add(user.getPicUrl());
           /* ImageIcon icon = new ImageIcon(user.getInfo());//图片处理
            icon.setImage(icon.getImage().getScaledInstance(100, 30,Image.SCALE_DEFAULT));
            but.setIcon(icon);*/
            rowV.add(user.getPicUrl());
            rowV.add(user.getPublishDate());  //数据

            dtm.addRow(rowV);
        }
        //	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  //关闭表格列的自动调整功能。
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //单选
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setRowHeight(30);
    }

    public void batchAddFilms( File file ) throws Exception {

        InputStream is = new FileInputStream(file.getAbsolutePath());       // 创建输入流，读取Excel
        Workbook wb = Workbook.getWorkbook(is);                             // jxl提供的Workbook类
        Sheet sheet = wb.getSheet("Sheet1");                             // 创建一个Sheet对象

        List<List> outerList= new ArrayList<>();                            //创建外list，存储每个电影

        for (int i = 1; i < sheet.getRows(); i++) {                         // sheet.getRows()返回该页的总行数
            List innerList=new ArrayList();                                     //创建内list，存储每个电影的具体内容

            for (int j = 0; j < sheet.getColumns(); j++) {                  // sheet.getColumns()返回该页的总列数
                String cellInfo = sheet.getCell(j, i).getContents();        //获取单元格内的内容
                if(cellInfo.isEmpty()){
                    continue;
                }
                innerList.add(cellInfo);                                    //内list添加内容
            }
            outerList.add(i-1, innerList);                                    //外list添加内容
        }

        for (int i = 0; i < outerList.size(); i++) {
            List temp = outerList.get(i);
            for (int j = 0; j < temp.size(); j++) {
                System.out.println(temp.get(j));
            }
        }

        if (cc.addMovie((outerList))==true){
            System.out.println("导入成功！");
        }
    }

    class MyTable  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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

    class dao  implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            {
                try {
                    batchAddFilms(new File(showFileOpenDialog()));
                    JOptionPane.showMessageDialog(null, "导入成功","成功",1);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                dtm.fireTableDataChanged();
            }
        }
    }

    class deleteMovie  implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
                int row= table.getSelectedRow(); //这句选择要删除的行
                if(row!=-1)//这句判断是否有选中的行
                    {
                    int id = (int)table.getValueAt(row, 0);
                    if(cc.deleteMovie(id)==true)
                        System.out.println("删除成功！");
                        dtm.removeRow(row); //这句删除指定行
                        JOptionPane.showMessageDialog(null, "删除成功","成功",1);
                     }
                     else{
                    System.out.println("删除失败！！");
            }
            dtm.fireTableDataChanged();
        }
    }

    class managerMovie  implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
            row= table.getSelectedRow(); //这句选择要管理的行
            if (row<0){
                JOptionPane.showMessageDialog(null, "未选择电影！","失败",1);
            }
            else{
                setFilmid((int)table.getValueAt(row, 0)); //这句选择要管理的电影ID
                FilmScreenManagerView fv = new FilmScreenManagerView();
                JFrame jf =new JFrame("场次管理");
                jf.setSize(700,780);
                jf.setLocationRelativeTo(null);
                JPanel jp = null;
                try {
                    jp = fv.findScreensByFilmId(getFilmid());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                jf.add(jp);
                jf.setVisible(true);
                dtm.fireTableDataChanged();
            }

        }
    }

}
