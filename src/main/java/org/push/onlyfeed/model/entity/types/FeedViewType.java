/**
 * 
 */
package org.push.onlyfeed.model.entity.types;

/**
 * @author push
 *
 */
public enum FeedViewType {
    COMPACT, 
    EXTENDED;
    
    public static final int LENGTH = FeedViewType.values().length;
    
    public static FeedViewType value(Byte val) {
        return FeedViewType.values()[val];
    }
}
