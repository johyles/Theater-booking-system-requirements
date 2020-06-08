package view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.sql.SQLException;

public class Main {

    public  static void main(String[] args) throws Exception {

        // 创建 JFrame 实例
        JFrame jf = new JFrame("管理员系统");
        jf.setSize(720, 650);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();

        // 创建第 1 个选项卡（选项卡只包含 标题）
        FilmManagerView aa=new FilmManagerView();
        JPanel jba=new JPanel();
        jba.setLayout(null);
        jba=aa.findFilmsByPage();
        tabbedPane.addTab("影片管理", new ImageIcon("bb.jpg"),jba,"影片信息");

        // 创建第 2 个选项卡（选项卡包含 标题 和 图标）
        TheaterManagerView theater=new TheaterManagerView();
        JPanel jpb=new JPanel();
        jpb=theater.jpbJpannel();
        tabbedPane.addTab("放映厅管理", new ImageIcon("bb.jpg"), jpb,"放映厅信息");

        // 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
        BookInfoManagerView cc=new BookInfoManagerView();
        JPanel jpc=new JPanel();
        jpc.setLayout(null);
        jpc=cc.jpcJpannel();

        tabbedPane.addTab("预定管理", new ImageIcon("bb.jpg"),jpc, "订票信息");


        GetTicketView dd=new GetTicketView();
        JPanel jpd=new JPanel();
        jpd = dd.getTicket();
        //JComponent jpd=createTextPanel();
        tabbedPane.addTab("取票", new ImageIcon("bb.jpg"), jpd, "取票");

        // 添加选项卡选中状态改变的监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex()+1);
            }
        });

        jf.setContentPane(tabbedPane);
        jf.setVisible(true);
    }

    private static JPanel createTextPanel() {

        JPanel panel = new JPanel();

        return panel;
    }
}
