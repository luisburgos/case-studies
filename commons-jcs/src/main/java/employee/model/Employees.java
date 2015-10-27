package employee.model;

import employee.misc.JSONtoCandidates;

import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class Employees extends Model {

    private static Employees employees;

    private ArrayList<Employee> candidates;

    private Employees(){
        super();
        candidates = JSONtoCandidates.loadCandidates();
    }

    public synchronized static Employees getInstance(){
        if(employees == null){
            employees = new Employees();
        }
        return employees;
    }

}
