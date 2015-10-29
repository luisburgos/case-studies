package employee.controller;

import employee.events.Event;
import employee.events.EventTypes;
import employee.events.NewEmployee;
import employee.misc.StringRes;
import employee.model.Employee;
import employee.model.Employees;
import employee.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by luisburgos on 26/10/15.
 */
public class AddEmployeeController extends Controller {

    private JFrame mainFrame;
    private JPanel panelButtons, panelFields;
    private JButton buttonAdd, buttonCancel;
    private JLabel labelName, labelEmail, labelAddress;
    private JTextField nameTextField, emailTextField, addressTextField;

    public AddEmployeeController(Model model, Event event) {
        super(model, event);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame();
        mainFrame.setSize(450, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /*mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                mainFrame.dispose();
            }
        });*/

        panelFields = new JPanel();

        labelName = new JLabel(StringRes.LABEL_NAME);
        nameTextField = new JTextField(10);
        labelEmail = new JLabel(StringRes.LABEL_EMAIL);
        emailTextField = new JTextField(30);
        labelAddress = new JLabel(StringRes.LABEL_ADDRESS);
        addressTextField = new JTextField(20);

        panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
        panelFields.add(labelName);
        panelFields.add(nameTextField);
        panelFields.add(labelEmail);
        panelFields.add(emailTextField);
        panelFields.add(labelAddress);
        panelFields.add(addressTextField);

        panelButtons = new JPanel();
        buttonAdd = new JButton(StringRes.BTN_ADD);
        buttonCancel = new JButton(StringRes.BTN_CANCEL);

        buttonAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Employee newEmployee = new Employee();
                newEmployee.setName(nameTextField.getText());
                newEmployee.setEmail(emailTextField.getText());
                newEmployee.setAddress(addressTextField.getText());
                handleEvent(new NewEmployee(EventTypes.NEW_EMPLOYEE), newEmployee);
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                mainFrame.dispose();
            }
        });

        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.add(buttonAdd);
        panelButtons.add(buttonCancel);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        mainFrame.add(panelFields);
        mainFrame.add(panelButtons);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void handleEvent(Event event, Object... params) {
        if(event.getType() == EventTypes.NEW_EMPLOYEE){
            mModel.callFunctionByName(Employees.class, Employee.class, "addNewEmployeeToDatabase", params);
        }
    }

    public void update(Event event) {
        //Do nothing
    }

    private void clearFields(){
        nameTextField.setText(null);
        emailTextField.setText(null);
        addressTextField.setText(null);
    }

}
