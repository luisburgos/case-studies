package employee.components;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AdapterTable extends AbstractTableModel {

    private final String[] columnNames;
    private final ArrayList<ArrayList<Object>> data;

    public AdapterTable(String[] columnNames) {
        this.columnNames = columnNames;
        data = new ArrayList<>();
    }

    public ArrayList getDataArray(){
        return this.data;
    }

    public void addRow(ArrayList row){
        data.add(row);
        fireTableDataChanged();
    }

    public void deleteRow(int index){
        data.remove(index);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }

    public void resetTable(){
        data.clear();
    }
}