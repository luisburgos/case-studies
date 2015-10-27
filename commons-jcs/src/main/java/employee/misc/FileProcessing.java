package employee.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProcessing {
    
    private static final String FILE_PATH = "src/assets/candidates.json";
    
    public String getInformation() throws FileNotFoundException, IOException {
              
        File file = new File(FILE_PATH);
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        return content;
    }   
}