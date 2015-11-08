package employee.notifications;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.*;
import java.util.ArrayList;

/**
 * Loads the Notification Manager configuration establish on the
 * notifications.yaml file.
 * Created by luisburgos on 6/11/15.
 */
public class NotificationManagerConfigurator {

    private static final String FILE_PATH = "notifications.yaml";

    /**
     * @return an instance of PushNotificationManager generated fromo the
     * configuration file.
     */
    public static PushNotificationManager createNotificationManagerFromConfigFile() {

        File file = new File((ClassLoader.getSystemResource(FILE_PATH).getFile()));
        YamlReader reader = null;
        PushNotificationManager notificationManager = null;
        try {
            reader = new YamlReader(new FileReader(file));
            notificationManager = reader.read(PushNotificationManager.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return notificationManager;
    }

}
