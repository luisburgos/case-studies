package employee.events;

/**
 * This class represents the event when a {@link employee.cache.CacheRegion} is
 * modified and holds the name of that region.
 *
 * Created by luisburgos on 2/11/15.
 */
public class CacheRegionModified extends Event {

    private String regionModifiedName;

    public CacheRegionModified() {
        super(EventTypes.CACHE_REGIN_MODIFIED);
        regionModifiedName = "";
    }

    public CacheRegionModified(String regionModifiedName) {
        super(EventTypes.CACHE_REGIN_MODIFIED);
        this.regionModifiedName = regionModifiedName;
    }

    public CacheRegionModified(int type, String eventInformation) {
        super(type, eventInformation);
    }

    public String getRegionModifiedName() {
        return regionModifiedName;
    }
}
