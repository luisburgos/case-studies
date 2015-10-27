package employee.misc;

import com.google.gson.Gson;
import employee.model.Employee;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luisburgos on 2/09/15.
 */
public class JSONtoCandidates {

    private static String FILE_PATH = "src/main/java/employee/assets/employees.json";

    public static ArrayList<Employee> loadCandidates() {

        Gson gson = new Gson();
        Employee[] result = null;
        ArrayList<Employee> candidates = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            result = gson.fromJson(br, Employee[].class);
            candidates = new ArrayList<Employee>();
            candidates.addAll(Arrays.asList(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candidates;
    }
}
