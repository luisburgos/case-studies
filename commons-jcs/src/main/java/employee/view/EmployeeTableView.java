package employee.view;

import employee.events.Event;
import employee.events.EventTypes;
import employee.misc.Observer;
import employee.model.Employee;
import employee.model.EmployeeCacheManager;
import employee.model.EmployeeDAO;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeTableView implements Observer {

    private AdapterTable modeloTabla;
    private JFrame tablaVentana;

    public EmployeeTableView() {
        initComponents();
    }

    private void initComponents() {
        JTable datosEmpleadosTabla = new JTable();
        //String[] nombreColumnas = {"ID","Name", "Email", "Address"};
        String[] nombreColumnas = {"Name", "Email", "Address"};
        modeloTabla = new AdapterTable(nombreColumnas);
        datosEmpleadosTabla.setModel(modeloTabla);

        tablaVentana = new JFrame("Datos de Empleados");
        JPanel panelContenedor = new JPanel();

        JScrollPane vistaDeTablaScroll = new JScrollPane();
        vistaDeTablaScroll.setViewportView(datosEmpleadosTabla);
        panelContenedor.add(vistaDeTablaScroll);

        tablaVentana.setContentPane(panelContenedor);
        tablaVentana.pack();
        tablaVentana.setLocationRelativeTo(null);
        tablaVentana.setVisible(true);
    }

    public void addEmployeeRow(Employee employee){

        ArrayList row = new ArrayList();
        //row.add(employee.getId());
        row.add(employee.getName());
        row.add(employee.getEmail());
        row.add(employee.getAddress());
        modeloTabla.addRow(row);
    }

    public void resetEmployeeTable(){
        modeloTabla.resetTable();
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
