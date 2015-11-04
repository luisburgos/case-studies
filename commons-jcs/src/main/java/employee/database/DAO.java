package employee.database;

import employee.model.entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Generic data access object definition for make transactios
 * to SQLite database.
 *
 * Created by luisburgos on 3/11/15.
 */
public abstract class DAO<K, V> {

    protected DatabaseSource db;
    protected Connection con;

    public DAO() {
        db = DatabaseSource.getDatabaseSource();
    }

    public abstract HashMap<K, V> getAll();
    public abstract boolean insert(V entity);
    public abstract V getById(int id);
    public abstract V getLastAdded();

}
