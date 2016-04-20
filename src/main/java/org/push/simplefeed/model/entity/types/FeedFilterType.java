/**
 * 
 */
package org.push.simplefeed.model.entity.types;

/**
 * @author push
 *
 */
public enum FeedFilterType {
    ALL,
    UNREAD,
    READ,
    LATEST_DAY;
      
    public static final int LENGTH = FeedFilterType.values().length;
    
    public static FeedFilterType value(Byte val) {
        return FeedFilterType.values()[val];
    }
}
