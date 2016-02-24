/**
 * 
 */
package org.push.takerss.model.service.impl;

import java.util.ArrayList;

import org.push.takerss.model.service.IRssChannelService;
import org.push.takerss.model.entity.RssChannelEntity;
import org.springframework.stereotype.Service;


/**
 * @author push
 *
 */
@Service
public class RssChannelService implements IRssChannelService {
    private ArrayList<RssChannelEntity> rssChannelList;
    
    
    public RssChannelService() {
        rssChannelList = new ArrayList<RssChannelEntity>();
    }
    

    @Override
    public void save(RssChannelEntity rssChannel) {
        rssChannelList.add(rssChannel);
    }


    @Override
    public RssChannelEntity findById(int id) {
        return rssChannelList.get(id);
    }


    @Override
    public ArrayList<RssChannelEntity> getAll() {
        return rssChannelList;
    }
}
