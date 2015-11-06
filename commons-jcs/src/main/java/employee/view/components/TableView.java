package employee.view.components;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by luisburgos on 1/11/15.
 */
public abstract class TableView {

    private AdapterTable mAdapterTable;
    private JFrame mMainFrame;
    private JTable mTable;
    private JPanel mTableContainer;
    private String[] mColumnNames;
    private String mFrameTitle;

    public TableView(String framTitle, String[] columnNames){
        mFrameTitle = framTitle;
        mColumnNames = columnNames;
        initComponents();
    }

    private void initComponents() {
        mTable = new JTable();
        mAdapterTable = new AdapterTable(mColumnNames);
        mTable.setModel(mAdapterTable);

        mMainFrame = new JFrame(mFrameTitle);
        mTableContainer = new JPanel();

        JScrollPane mTableScrollView = new JScrollPane();
        mTableScrollView.setViewportView(mTable);
        mTableContainer.add(mTableScrollView);

        mMainFrame.setContentPane(mTableContainer);
        mMainFrame.pack();
        mMainFrame.setLocationRelativeTo(null);
        mMainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mMainFrame.setVisible(true);
    }

    public void resetTable(){
        mAdapterTable.resetTable();
    }

    protected void add(ArrayList row){
        mAdapterTable.addRow(row);
    }

}
