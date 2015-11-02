package employee;

import employee.controller.AddEmployeeController;
import employee.controller.Controller;
import employee.events.*;
import employee.model.Employee;
import employee.model.EmployeeCacheManager;
import employee.model.Employees;
import employee.model.Model;
import employee.view.EmployeeTableView;
import employee.view.EntityInputForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    private Model mModel;
    private Controller mController;
    private EmployeeTableView mTable;

    public App (){
        mModel = Employees.getInstance();
        mController = new AddEmployeeController(mModel, new NewEmployee());
        mTable = new EmployeeTableView();

        EmployeeCacheManager.getManager().register(new Startup(), mTable);
        mModel.register(new NewEmployee(), mTable);
        mModel.register(new Startup(), EmployeeCacheManager.getManager());
        mModel.register(new Shutdown(), EmployeeCacheManager.getManager());

        init();
    }

    private void init(){
        Employees.getInstance().notify(new Startup());
    }

    public static void main(String[] args) {
        new App();
    }


}
