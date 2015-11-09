package employee.controller;

import employee.misc.events.*;
import employee.model.entities.Employee;
import employee.model.Employees;
import employee.model.Model;
import employee.view.components.EntityInputForm;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by luisburgos on 26/10/15.
 */
public class AddEmployeeController extends Controller {

    private EntityInputForm inputForm;

    public AddEmployeeController(Model model, Event event) {
        super(model, event);
        inputForm = new EntityInputForm
                .Builder(Employee.class)
                .setAddAction(buttonAddActionListener)
                .setCancelAction(buttonCancelActionListener)
                .setCloseAction(windowCloseListener)
                .build();
    }

    /**
     * Triggers the function related to the event type
     * @param event
     * @param params
     */
    public void handleEvent(Event event, Object... params) {
        if(event.getType() == EventTypes.NEW_EMPLOYEE){
            mModel.callFunctionByName(Employees.class, Employee.class, "addNewEmployeeToDatabase", params);
            inputForm.clearLabelFields();
        }

        if(event.getType() == EventTypes.SHUTDOWN){
            mModel.notify(event);
        }
    }

    public void update(Event event) {
        //Do nothing
    }

    private boolean isEmployeeValid(Employee newEmployee) {
        boolean nameValid = newEmployee.getName() != null && !newEmployee.getName().isEmpty();
        boolean emailValid = newEmployee.getEmail() != null && !newEmployee.getEmail().isEmpty();
        boolean addressValid = newEmployee.getAddress() != null && !newEmployee.getAddress().isEmpty();

        return (nameValid && emailValid && addressValid);
    }

    ActionListener buttonAddActionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            Employee newEmployee = new Employee();
            newEmployee.setName(inputForm.getTextFildByName("name").getText());
            newEmployee.setEmail(inputForm.getTextFildByName("email").getText());
            newEmployee.setAddress(inputForm.getTextFildByName("address").getText());
            if(isEmployeeValid(newEmployee)){
                handleEvent(new NewEmployee(), newEmployee);
            }else {
                JOptionPane.showMessageDialog (null, "Fill all fields", "Empty fields", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    };

    ActionListener buttonCancelActionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            handleEvent(new Shutdown());
            inputForm.dispose();
        }
    };

    WindowListener windowCloseListener = new WindowAdapter() {
        public void windowClosing(WindowEvent windowEvent) {
            handleEvent(new Shutdown());
            System.exit(WindowConstants.EXIT_ON_CLOSE);
        }
    };

}
