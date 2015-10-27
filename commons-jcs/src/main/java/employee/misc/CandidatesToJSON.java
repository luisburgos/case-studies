package employee.misc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import employee.model.Employee;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luisburgos on 14/09/15.
 */
public class CandidatesToJSON  {

    private static String FILE_PATH = "src/main/java/candidates.json";

    public static void saveCandidates(ArrayList<Employee> candidates){

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(candidates, new TypeToken<List<Employee>>() {
        }.getType());

        if (! element.isJsonArray()) {
            //throw new SomeException();
        }
        JsonArray jsonArray = element.getAsJsonArray();

        try {
            FileWriter file = new FileWriter(FILE_PATH);
            file.write(jsonArray.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
