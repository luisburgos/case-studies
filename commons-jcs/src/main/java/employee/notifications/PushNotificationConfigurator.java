package employee.notifications;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by luisburgos on 6/11/15.
 */
public class PushNotificationConfigurator {

    public static PushNotificationManager getNotificationManagerFromConfigFile() {

        File file = new File((ClassLoader.getSystemResource("notifications.yaml").getFile()));
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
