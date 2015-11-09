package employee.cache;

import employee.model.entities.Employee;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.apache.commons.jcs.engine.control.CompositeCache;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents an specific cache region defined and
 * provides methods to modify information that belongs to
 * that specific cache region.
 *
 * Created by luisburgos on 2/11/15.
 */
public class CacheRegion {

    private String regionName;
    private CacheAccess<Object, Object> cache = null;

    public CacheRegion(String regionName) {
        try{
            cache = JCS.getInstance(regionName);
        }catch(CacheException e){
            e.printStackTrace();
        }
        this.regionName = regionName;
    }

    /**
     * Inserts a new element to this cache region.
     * @param key identifier of the Object
     * @param value the Object to insert
     */
    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    /**
     * Removes an Object given a key.
     * @param key of the Object to remove.
     */
    public void remove(Object key) {
        cache.remove(key);
    }

    /**
     * Deletes all information in this cache region.
     */
    public void dropCacheData() {
        System.out.println("Dropping all cache data from" + regionName);
        for(Object key : getCacheKeys()){
            cache.remove(key);
        }
    }

    /**
     * @return an ArrayList of Objects representing all the
     * data contained in this region.
     */
    public ArrayList<Object> getAll() {
        ArrayList<Object> all = new ArrayList<>();
        for(Object key : getCacheKeys()){
            Object e = cache.get(key);
            if(e != null){
                all.add(e);
            }
        }
        return all;
    }

    /**
     * @return an Object representing the last element added to this region.
     */
    public Object getLast() {
        Set<Object> keys = getCacheKeys();
        return cache.get(keys.size());
    }

    /**
     * @return a Set of keys of all elements in this specific region.
     */
    private Set<Object> getCacheKeys() {
        return  CompositeCacheManager
                .getInstance(regionName)
                .getCache(regionName)
                .getKeySet();
    }
}
