package employee;

import employee.controller.AddEmployeeController;
import employee.controller.Controller;
import employee.events.NewEmployee;
import employee.model.Employee;
import employee.model.EmployeeManager;
import employee.model.Employees;
import employee.model.Model;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    public static void main(String[] args) {

        Model employees = Employees.getInstance();

        Controller addEmployeeCtrl = new AddEmployeeController(employees, new NewEmployee(1));
        
        employees.register(new NewEmployee(1), addEmployeeCtrl);
    }

}
