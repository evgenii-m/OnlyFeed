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
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.FeedItemEntity;
import org.push.simplefeed.model.repository.FeedSourceRepository;
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
        List<FeedSourceEntity> feedSourceList = new ArrayList<>();
        FeedSourceEntity feedSource = new FeedSourceEntity();
        feedSource.setName("Хабрахабр / Интересные публикации");
        feedSource.setUrl("https://habrahabr.ru/rss/interesting/");
        feedSourceList.add(feedSource);
        
        FeedSourceRepository feedSourceRepository = mock(FeedSourceRepository.class);
        when(feedSourceRepository.findAll()).thenReturn(feedSourceList);
        
        RssService rssService = new RssService();
        rssService.setXmlConverter(xmlConverter);
        
        feedItemService = new FeedItemService();
        feedItemService.setFeedSourceRespository(feedSourceRepository);
        feedItemService.setRssService(rssService);
    }
    
    /**
     * Test method for {@link org.push.simplefeed.model.service.FeedItemService#getAll()}.
     */
    @Test
    public void testGetAll() {        
        List<FeedItemEntity> feedItemList = feedItemService.getAll();
        assertNotNull("Service return null", feedItemList);
        
        for (FeedItemEntity feedItem : feedItemList) {
            assertTrue("Feed Item Link corrupt", feedItem.getLink().matches(itemLinkMatchPattern));
            System.out.println("Description: " + feedItem.getDescription());
            System.out.println("Image URL: " + feedItem.getImageUrl());
            System.out.println("Brief Description:" + feedItem.getBriefDescription() + 
                    "\n--------------------------------------------------------------------------------------\n");
        }        
    }
    
}
