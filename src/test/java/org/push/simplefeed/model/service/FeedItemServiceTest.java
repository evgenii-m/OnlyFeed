/**
 * 
 */
package org.push.simplefeed.model.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.push.simplefeed.model.entity.FeedChannelEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.repository.FeedChannelRepository;
import org.push.simplefeed.util.xml.XmlConverter;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author push
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/xml-converter-context.xml")
public class FeedItemServiceTest {
    @Autowired
    private XmlConverter xmlConverter;
    private FeedItemService feedItemService;
    private String itemLinkMatchPattern = "https://habrahabr.ru/post/[0-9]+/";
    
    
    @Before
    public void prepareTestEnvironment() {
        List<FeedChannelEntity> feedChannelList = new ArrayList<>();
        feedChannelList.add(new FeedChannelEntity(
                "Хабрахабр / Интересные публикации", "https://habrahabr.ru/rss/interesting/"));
        
        FeedChannelRepository feedChannelRepository = mock(FeedChannelRepository.class);
        when(feedChannelRepository.findAll()).thenReturn(feedChannelList);
        
        RssService rssService = new RssService();
        rssService.setXmlConverter(xmlConverter);
        
        feedItemService = new FeedItemService();
        feedItemService.setFeedChannelRespository(feedChannelRepository);
        feedItemService.setRssService(rssService);
    }
    
    /**
     * Test method for {@link org.push.simplefeed.model.service.FeedItemService#findAll()}.
     */
    @Test
    public void testFindAll() {        
        List<FeedItemEntity> feedItemList = feedItemService.findAll();
        assertNotNull("Service return null", feedItemList);
        
        for (FeedItemEntity feedItem : feedItemList) {
            assertTrue("Feed Item Link corrupt", feedItem.getLink().matches(itemLinkMatchPattern));
        }
    }
    
}
