/**
 * 
 */
package org.push.takerss.model.service;

import java.util.ArrayList;

import org.push.takerss.model.entity.RssChannelEntity;


/**
 * @author push
 *
 */
public interface IRssChannelService {
    void save(RssChannelEntity rssChannel); 
    RssChannelEntity findById(int id);
    ArrayList<RssChannelEntity> getAll();
}
