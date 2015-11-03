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

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public void remove(Object key) {
        cache.remove(key);
    }

    public void dropCacheData() {
        System.out.println("Dropping all cache data");
        for(Object key : getCacheKeys()){
            cache.remove(key);
        }
    }

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

    public Object getLast() {
        Set<Object> keys = getCacheKeys();
        return cache.get(keys.size());
    }


    private Set<Object> getCacheKeys() {
        return  CompositeCacheManager
                .getInstance(regionName)
                .getCache(regionName)
                .getKeySet();
    }
}
