package employee.notifications;

/**
 * Created by luisburgos on 7/11/15.
 */
public class Notification {

    private String from;
    private Object data;

    public String getFrom() {
        return from;
    }

    public Notification setFrom(String from) {
        this.from = from;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Notification setData(Object data) {
        this.data = data;
        return this;
    }

}
