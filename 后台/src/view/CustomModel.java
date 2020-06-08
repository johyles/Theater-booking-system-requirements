package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

class CustomModel extends DefaultTableModel {

    /*public CustomModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }*/

    public CustomModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public Class getColumnClass(int col) {
        Vector v = (Vector)dataVector.elementAt(0);
        return v.elementAt(col).getClass();
    }
    public boolean isCellEditable(int row, int col) {
        Class columnClass = getColumnClass(col);
        return columnClass != ImageIcon.class &&columnClass != Date.class;
    }
}
