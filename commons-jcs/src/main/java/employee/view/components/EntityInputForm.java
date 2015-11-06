package employee.view.components;

import employee.misc.StringRes;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by luisburgos on 1/11/15.
 */
public class EntityInputForm {

    private JFrame mMainFrame;
    private JPanel mButtonsPanel, mRegionsPanel;
    private JButton mAddButton;
    private JButton mCancelButton;
    private HashMap<String, JLabel> mLabels;
    private HashMap<String, JTextField> mFields;

    public JTextField getTextFildByName(String name) {
        return mFields.get(name);
    }

    public void clearLabelFields(){
        for(JTextField textField : mFields.values()){
            textField.setText(null);
        }
    }

    private EntityInputForm(Builder builder){
        initGUI();
        composeGUI(builder.mEntity);

        this.mAddButton.addActionListener(builder.mButtonAddListener);
        this.mCancelButton.addActionListener(builder.mButtonCancelListener);
        this.mMainFrame.addWindowListener(builder.mWindowCloseListener);

        mButtonsPanel.setLayout(new BoxLayout(mButtonsPanel, BoxLayout.X_AXIS));
        mButtonsPanel.add(mAddButton);
        mButtonsPanel.add(mCancelButton);

        mMainFrame.setLayout(new BoxLayout(mMainFrame.getContentPane(), BoxLayout.Y_AXIS));
        mMainFrame.add(mRegionsPanel);
        mMainFrame.add(mButtonsPanel);
        mMainFrame.pack();
        mMainFrame.setVisible(true);
    }

    private void composeGUI(Class mEntity) {
        for(Field field : mEntity.getDeclaredFields()){
            String name = field.getName();
            if(!name.contains("id")){
                addLabel(name);
                addTextField(name);
            }
        }
    }

    private void addTextField(String name) {
        JTextField textField = new JTextField(30);
        mFields.put(name, textField);
        mRegionsPanel.add(textField);
    }

    private void addLabel(String name) {
        JLabel label = new JLabel(getCapitalizedName(name));
        mLabels.put(name, label);
        mRegionsPanel.add(label);
    }

    private String getCapitalizedName(String inputName) {
        String firstLetterCapitalized = inputName.substring(0, 1).toUpperCase();
        String restOfTheWord = inputName.substring(1);
        String nameCapitalized = firstLetterCapitalized + restOfTheWord;
        return nameCapitalized;
    }


    private void initGUI(){
        mMainFrame = new JFrame();
        mMainFrame.setSize(450, 200);
        mMainFrame.setLocationRelativeTo(null);

        mRegionsPanel = new JPanel();
        mRegionsPanel.setLayout(new BoxLayout(mRegionsPanel, BoxLayout.Y_AXIS));

        mLabels = new HashMap<>();
        mFields = new HashMap<>();
        mButtonsPanel = new JPanel();

        mAddButton = new JButton(StringRes.BTN_ADD);
        mCancelButton = new JButton(StringRes.BTN_CANCEL);

    }

    public void dispose() {
        mMainFrame.dispose();
    }

    public static class Builder {
        private Class mEntity;
        private ActionListener mButtonAddListener;
        private ActionListener mButtonCancelListener;
        private WindowListener mWindowCloseListener;

        public Builder(Class entity){
            if(entity == null){
                throw new IllegalArgumentException("Class can not be null");
            }
            mEntity = entity;
        }

        public Builder setAddAction(ActionListener addAction){
            mButtonAddListener = addAction;
            return this;
        }

        public Builder setCancelAction(ActionListener cancelAction){
            mButtonCancelListener = cancelAction;
            return this;
        }

        public Builder setCloseAction(WindowListener closeAction){
            mWindowCloseListener = closeAction;
            return this;
        }

        public EntityInputForm build(){
            return new EntityInputForm(this);
        }

    }

}
