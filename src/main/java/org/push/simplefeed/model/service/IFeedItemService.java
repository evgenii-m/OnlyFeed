/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.List;

import org.push.simplefeed.model.entity.FeedItemEntity;
import org.springframework.oxm.XmlMappingException;

/**
 * @author push
 *
 */
public interface IFeedItemService {
    public List<FeedItemEntity> findAll() throws XmlMappingException, IOException;
}
