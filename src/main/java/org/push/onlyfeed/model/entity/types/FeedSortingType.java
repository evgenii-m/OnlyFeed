/**
 * 
 */
package org.push.onlyfeed.model.entity.types;

/**
 * @author push
 *
 */
public enum FeedSortingType {
    NEWEST_FIRST,
    OLDEST_FIRST;
    
    public static final int LENGTH = FeedSortingType.values().length;
  
    public static FeedSortingType value(Byte val) {
        return FeedSortingType.values()[val];
    }
}
