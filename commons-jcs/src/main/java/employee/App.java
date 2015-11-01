package employee;

import employee.controller.AddEmployeeController;
import employee.controller.Controller;
import employee.events.*;
import employee.model.Employee;
import employee.model.EmployeeCacheManager;
import employee.model.Employees;
import employee.model.Model;
import employee.view.EmployeeTableView;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    public static void main(String[] args) {

        Model employees = Employees.getInstance();
        Controller addEmployeeCtrl = new AddEmployeeController(employees, new NewEmployee());
        EmployeeTableView table = new EmployeeTableView();

        EmployeeCacheManager.getManager().register(new Startup(), table);


        employees.register(new NewEmployee(), table);
        //employees.register(new Startup(), table);
        employees.register(new Startup(), EmployeeCacheManager.getManager());
        employees.register(new Shutdown(), EmployeeCacheManager.getManager());

        employees.notify(new Startup());
    }

}
