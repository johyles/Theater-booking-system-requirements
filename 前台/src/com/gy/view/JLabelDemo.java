package com.gy.view;

import com.gy.action.*;
import com.gy.entity.BookInfo;
import com.gy.entity.Film;
import com.gy.entity.FilmScreen;
import com.gy.entity.Theater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class JLabelDemo
{

    public static void buyTicket() throws SQLException {
        JFrame frame=new JFrame("购票页面");
        Conn conn = new Conn();
        int count = conn.getCount();
        int pageNum = 1;
        int pageSize;
        if(count%2==0)
        {
            pageSize = count / 2;
        }else
        {
            pageSize = (count+1) / 2;
        }
        page(frame,pageNum,pageSize);
    }

    public static void page(JFrame frame,int pageNum,int pageSize) throws SQLException {    //选票页面
        FilmExport filmExport = new FilmExport();
        JPanel jp = new JPanel();
        jp.setLayout(null);
        for(int i=0;i<2;i++)
        {
            Film film = filmExport.Import(2*pageNum-1+i);//创建面板
            ImageIcon img = new ImageIcon(film.getUrl());
            img.setImage(img.getImage().getScaledInstance(90, 150, Image.SCALE_DEFAULT));
            ImageIcon img2 = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\book.png");
            img2.setImage(img2.getImage().getScaledInstance(50, 30, Image.SCALE_DEFAULT));
            JLabel label1 = new JLabel(img);
            label1.setBounds(20, 10+154*i, 90, 150);
            JLabel label2 = new JLabel("<html>" + film.getName() + "<br>" + film.getIntroduce() + "<br><br><br><br><br>" + film.getDate() + "</html>");
            label2.setBounds(110, 10+150*i, 90, 150);
            JButton btn3 = new JButton(img2);
            btn3.setFocusPainted(false);
            btn3.setBounds(200, 70+150*i, 50, 30);
            btn3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        selectTime(film.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            jp.add(label1);    //添加标签到面板
            jp.add(label2);
            jp.add(btn3);
        }
        ImageIcon img5 = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\pre.png");
        img5.setImage(img5.getImage().getScaledInstance(70, 40, Image.SCALE_DEFAULT));
        JButton btn1 = new JButton(img5);
        btn1.setBounds(40, 330, 70, 40);
        btn1.setFocusPainted(false);
        if (pageNum == 1) {
            btn1.setEnabled(false);
        } else {
            int p = pageNum - 1;
            btn1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    frame.getContentPane().removeAll();
                    try {
                        page(frame,p,pageSize);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ImageIcon img4 = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\next.png");
        img4.setImage(img4.getImage().getScaledInstance(70, 40, Image.SCALE_DEFAULT));
        JButton btn2 = new JButton(img4);
        btn2.setFocusPainted(false);
        if(pageNum == pageSize)
        {
            btn2.setEnabled(false);
        }else {
            int p = pageNum+1;
            btn2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    frame.getContentPane().removeAll();
                    try{
                        page(frame,p,pageSize);
                    }catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        btn2.setBounds(160, 330, 70, 40);
        jp.add(btn1);
        jp.add(btn2);
        frame.add(jp);
        frame.setBounds(500,300,290,430);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void show(JFrame frame,JPanel jp1,int screenId) throws SQLException {     //选位置页面
        Book bk = new Book();
        List<BookInfo> fs = new ArrayList<BookInfo>();
        try {
            fs = bk.bookImport(screenId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ImageIcon img1=new ImageIcon("C:\\Users\\16090\\Desktop\\img\\screen.png");
        img1.setImage(img1.getImage().getScaledInstance(400, 40, Image.SCALE_DEFAULT));
        JLabel label0=new JLabel(img1);
        ImageIcon img = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\已选.png");
        img.setImage(img.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        ImageIcon img3 = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\可选.png");
        img3.setImage(img3.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        JLabel label1 = new JLabel();
        FilmScreenInfo fsi = new FilmScreenInfo();
        int theaterId=fsi.selectByScreenId(screenId);
        Theater tr = new Theater();
        TheaterInfo ti = new TheaterInfo();
        tr = ti.theaterImport(theaterId);
        int [][]seat = new int[tr.getRows()][tr.getColumns()] ;
        if(tr.getRows()>=6) {
            label1.setText(tr.getName());
            label0.setBounds(260,0,200+22*tr.getRows(),50);
            jp1.add(label0);
            label1.setBounds(295+tr.getRows()*17,50,50,50);
            jp1.add(label1);
            for (int i = 0; i < tr.getRows(); i++) {
                for (int j = 0; j < tr.getColumns(); j++) {
                    seat[i][j] = 0;
                    JButton btn = new JButton(img3);
                    btn.setBorder(null);
                    btn.setBounds(300 + i * 43, 100 + j * 45, 35, 35);
                    jp1.add(btn);
                    for (int k = 0; k < fs.size(); k++) {
                        if (fs.get(k).getSeatX() == (i + 1) && fs.get(k).getSeatY() == (j + 1)) {
                            btn.setIcon(img);
                            btn.setEnabled(false);
                        }
                    }
                    int[] click = {0};
                    int finalI = i;
                    int finalJ = j;
                    Theater finalTr1 = tr;
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            click[0]++;
                            if (click[0] % 2 == 0) {
                                btn.setIcon(img3);
                                seat[finalI][finalJ] = 0;
                            } else {
                                btn.setIcon(img);
                                seat[finalI][finalJ] = 1;
                            }
                        }
                    });
                }
            }
        }else
        {
            label1.setText(tr.getName());
            label0.setBounds(260,0,200+22*tr.getRows(),50);
            jp1.add(label0);
            label1.setBounds(300+tr.getRows()*20,50,50,50);
            jp1.add(label1);
            for (int i = 0; i < tr.getRows(); i++) {
                for (int j = 0; j < tr.getColumns(); j++) {
                    seat[i][j] = 0;
                    JButton btn = new JButton(img3);
                    btn.setBorder(null);
                    btn.setBounds(290 + i * 55, 100 + j * 50, 35, 35);
                    jp1.add(btn);
                    for (int k = 0; k < fs.size(); k++) {
                        if (fs.get(k).getSeatX() == (i + 1) && fs.get(k).getSeatY() == (j + 1)) {
                            btn.setIcon(img);
                            btn.setEnabled(false);
                        }
                    }
                    int[] click = {0};
                    int finalI = i;
                    int finalJ = j;
                    Theater finalTr1 = tr;
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            click[0]++;
                            if (click[0] % 2 == 0) {
                                btn.setIcon(img3);
                                seat[finalI][finalJ] = 0;
                            } else {
                                btn.setIcon(img);
                                seat[finalI][finalJ] = 1;
                            }
                        } 
                    });
                }
            }
        }

        ImageIcon img4 = new ImageIcon("C:\\Users\\16090\\Desktop\\img\\submitBtn.png");
        img4.setImage(img4.getImage().getScaledInstance(100, 40, Image.SCALE_DEFAULT));
        JButton btn3 = new JButton();
        btn3.setIcon(img4);
        btn3.setBounds(245+tr.getRows()*23,500,100,40);
        jp1.add(btn3);
        List<BookInfo> list = new ArrayList<BookInfo>();
        Theater finalTr = tr;
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String inputValue = JOptionPane.showInputDialog("请输入11位电话号码:");
                while (inputValue.length()!=11)
                {
                    inputValue = JOptionPane.showInputDialog("输入号码有误，请重新输入:");
                }
                GetRandom grd = new GetRandom();
                String rds = grd.randoms();
                JOptionPane.showMessageDialog(null,"<html>预定成功:<br>电话号码:"+inputValue+"<br>取票码:"+rds+"</html>","成功",1);
                int maxId = bk.maxId();
                for (int i = 0; i< finalTr.getRows(); i++)
                {
                    for(int j = 0; j< finalTr.getColumns(); j++)
                    {
                        if(seat[i][j]==1)
                        {
                            maxId++;
                            BookInfo bk2 = new BookInfo();
                            bk2.setId(maxId);
                            bk2.setScreenId(screenId);
                            bk2.setSeatY(j+1);
                            bk2.setSeatX(i+1);
                            bk2.setValidCode(rds);
                            bk2.setPhone(inputValue);
                            bk2.setStatus(0);
                            list.add(bk2);
                        }
                    }
                }
                Book bk1 = new Book();
                bk1.exportData(list);
                frame.dispose();
            }
        });
        jp1.setBounds(250,0,200,500);
        frame.add(jp1);
        frame.setBounds(500,200,700,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void selectTime(int filmId) throws SQLException {        //选日期页面
        JFrame frame=new JFrame("购票选时间段页面");
        JPanel jp = new JPanel()
        {
            public void paint(Graphics g)
            {
                super.paint(g);
                super.repaint();
            }
        };
        jp.setLayout(null);
        List<FilmScreen> list1 = new ArrayList<FilmScreen>();
        FilmScreenInfo fso = new FilmScreenInfo();
        list1 = fso.screenByFilmId(filmId);
        JLabel jlabel = new JLabel("请选择日期");
        JComboBox cmb=new JComboBox();    //创建JComboBox
        cmb.addItem("--请选择--");       /* 向下拉列表中添加一项 */
        List<FilmScreen> list2 = new ArrayList<FilmScreen>();
        list2 = fso.startDayD(list1);
        for(int i=0;i<list2.size();i++)
        {
            cmb.addItem(list2.get(i).getStartDay());
        }
        List<FilmScreen> finalList1 = list1;
        cmb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                Date text= (Date) cmb.getSelectedItem();
                if (itemEvent.getStateChange() == ItemEvent.SELECTED)
                {
                    int ctCount = jp.getComponentCount();
                    for(int i = ctCount-1;i >=2;i--){
                        jp.remove(i);
                    }
                    jp.repaint();
                    int k =0 ;
                    int count = 0;
                    for(int i1=0;i1<finalList1.size();i1++) {
                        if (text.getTime() == finalList1.get(i1).getStartDay().getTime()) {
                            count++;
                        }
                    }
                    for(int i=0;i<finalList1.size();i++)
                    {
                        if (text.getTime() == finalList1.get(i).getStartDay().getTime())
                        {
                            JButton btn = new JButton(finalList1.get(i).getTime());
                            btn.setFocusPainted(false);
                            int screenId = finalList1.get(i).getId();
                            btn.setBounds(90, 30 + k * 30, 100, 20);
                            int finalCount = count;
                            btn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    try {
                                        int ctCount = jp.getComponentCount();
                                        for(int i = ctCount-1; i>=2+ finalCount; i--)
                                        {
                                            jp.remove(i);
                                        }
                                        jp.repaint();
                                        show(frame,jp,screenId);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            jp.add(btn);
                            k++;
                        }
                    }
                }
            }
        });
        jlabel.setBounds(0,0,90,20);
        jp.add(jlabel);
        cmb.setBounds(90,0,100,20);
        jp.add(cmb);
        jp.setBounds(0,0,200,500);
        frame.add(jp);
        frame.setBounds(500,200,700,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void main(String[] agrs) throws SQLException {
        buyTicket();
    }

}