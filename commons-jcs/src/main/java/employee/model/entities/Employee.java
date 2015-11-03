package employee.model.entities;

import java.io.Serializable;

/**
 * This class implements Serializable, which is required for all objects that you add to a cache because the
 * cache uses serialization to read and write objects to persistent storage.
 *
 * Created by luisburgos on 26/10/15.
 */
public class Employee implements Serializable {

    private int id;
    private String name;
    private String email;
    private String address;

    public Employee() {
        this.id = -1;
    }

    public Employee(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
