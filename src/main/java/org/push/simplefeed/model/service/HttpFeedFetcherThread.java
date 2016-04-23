/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.InputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

/**
 * @author push
 *
 */
public class HttpFeedFetcherThread extends Thread {
    private static Logger logger = LogManager.getLogger(HttpFeedFetcherThread.class);
    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private final int id;
    private SyndFeed syndFeed;

    public HttpFeedFetcherThread(CloseableHttpClient httpClient, HttpGet httpget, int id) {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httpget = httpget;
        this.id = id;
        this.syndFeed = null;
    }

    
    /**
     * Executes the GET Method and read feed from response.
     */
    @Override
    public void run() {
        try {
            logger.debug(id + " - GET feed from " + httpget.getURI());
            try (CloseableHttpResponse response = httpClient.execute(httpget, context);
                    InputStream stream = response.getEntity().getContent()) {
                logger.debug(id + " response received");
                SyndFeedInput input = new SyndFeedInput();
                syndFeed = input.build(new XmlReader(stream));
                logger.debug(id + " feed ready");
            }
        } catch (Exception e) {
            logger.error("Exception on HttpFeedFetcherThread (id=" + id + ", URL=" 
                    + httpget.getURI() +", exception: " + e + ")");
            e.printStackTrace();
        }
    }
    
    
    public SyndFeed getSyndFeed() {
        return syndFeed;
    }
    
}
