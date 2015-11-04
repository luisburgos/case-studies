package employee.cache;

import org.apache.commons.jcs.access.exception.CacheException;

/**
 * Created by luisburgos on 2/11/15.
 */
public class RegionNotFoundException extends CacheException {

    private static final String MESSAGE = "Cannot find the specific region given";

    public RegionNotFoundException() {
        super(MESSAGE);
    }

    public RegionNotFoundException(String message) {
        super(message);
    }
}
