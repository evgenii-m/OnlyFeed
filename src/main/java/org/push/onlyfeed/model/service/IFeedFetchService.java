/**
 * 
 */
package org.push.onlyfeed.model.service;

import java.util.Map;

import com.rometools.rome.feed.synd.SyndFeed;

/**
 * @author push
 *
 */
public interface IFeedFetchService {
    SyndFeed retrieveFeed(String sourceUrl);
    Map<String, SyndFeed> retrieveFeeds(String[] sourcesUrl);
}
