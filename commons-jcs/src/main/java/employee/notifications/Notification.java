package employee.notifications;

/**
 * Created by luisburgos on 6/11/15.
 */
public class Notification {

    private int id;
    private Object data;

    public Notification(int id, Object data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
