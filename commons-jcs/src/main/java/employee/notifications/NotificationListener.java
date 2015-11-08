package employee.notifications;

/**
 * Provides a holder for the information of the listener or
 * client that wants to be notified with data from the
 * {@link PushNotificationSystem}.
 * The instance variable is obtained from the client registration on
 * the {@link PushNotificationSystem#register(Object)} method.
 *
 * Created by luisburgos on 6/11/15.
 */
public class NotificationListener {

    private Object instance;
    private String classpath;
    private String method;

    public NotificationListener() { }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
