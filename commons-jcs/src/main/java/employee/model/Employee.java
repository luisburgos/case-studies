package employee.model;

import java.io.Serializable;

/**
 * This class implements Serializable, which is required for all objects that you add to a cache because the
 * cache uses serialization to read and write objects to persistent storage.
 *
 * Created by luisburgos on 26/10/15.
 */
public class Employee implements Serializable{

    private String id;
    private String name;
    private String address;

    public Employee() {}

    public Employee(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
