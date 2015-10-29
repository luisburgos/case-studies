package employee.view;

import employee.events.Event;
import employee.events.EventTypes;
import employee.misc.Observer;
import employee.model.Employee;
import employee.model.EmployeeCacheManager;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeTableView implements Observer {

    private AdapterTable adapterTable;
    private JFrame employeesTable;

    public EmployeeTableView() {
        initComponents();
    }

    private void initComponents() {
        JTable datosEmpleadosTabla = new JTable();
        //String[] nombreColumnas = {"ID","Name", "Email", "Address"};
        String[] nombreColumnas = {"Name", "Email", "Address"};
        adapterTable = new AdapterTable(nombreColumnas);
        datosEmpleadosTabla.setModel(adapterTable);

        employeesTable = new JFrame("Empleados");
        JPanel panelContenedor = new JPanel();

        JScrollPane vistaDeTablaScroll = new JScrollPane();
        vistaDeTablaScroll.setViewportView(datosEmpleadosTabla);
        panelContenedor.add(vistaDeTablaScroll);

        employeesTable.setContentPane(panelContenedor);
        employeesTable.pack();
        employeesTable.setLocationRelativeTo(null);
        employeesTable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        employeesTable.setVisible(true);
    }

    public void addEmployeeRow(Employee employee){

        ArrayList row = new ArrayList();
        //row.add(employee.getId());
        row.add(employee.getName());
        row.add(employee.getEmail());
        row.add(employee.getAddress());
        adapterTable.addRow(row);
    }

    public void resetEmployeeTable(){
        adapterTable.resetTable();
    }

    public void update(Event event) {
        if(event.getType() == EventTypes.STARTUP){
            for(Employee e : EmployeeCacheManager.getManager().getAllEmployees()){
                addEmployeeRow(e);
            }
        }

        if(event.getType() == EventTypes.NEW_EMPLOYEE){
            addEmployeeRow(EmployeeCacheManager.getManager().getLastEmployeeAdded());
        }
    }

}
