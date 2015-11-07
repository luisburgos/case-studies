package employee.notifications;

/**
 * Created by luisburgos on 6/11/15.
 */
public class NotificationListener {

    private String classname;
    private String method;

    public NotificationListener() { }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "NotificationListener{" +
                "classname='" + classname + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
