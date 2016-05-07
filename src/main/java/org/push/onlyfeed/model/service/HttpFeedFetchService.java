/**
 * 
 */
package org.push.onlyfeed.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

/**
 * @author push
 *
 */
@Service
public class HttpFeedFetchService implements IFeedFetchService {
    private static Logger logger = LogManager.getLogger(HttpFeedFetchService.class);
            
    
    @Override
    public SyndFeed retrieveFeed(String sourceUrl) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet method = new HttpGet(sourceUrl);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            method.setConfig(requestConfig);
//          method.addHeader("If-Modified-Since", Date(lastModified));
            try (CloseableHttpResponse response = client.execute(method);
                    InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed syndFeed = input.build(new XmlReader(stream));
                return syndFeed;
            } catch (IllegalArgumentException | FeedException e) {
                logger.error("Exception when retrieveFeed from source (sourceUrl=" + sourceUrl
                        + ", exception: " + e + ")");
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            logger.error("Exception when retrieveFeed from source (sourceUrl=" + sourceUrl
                    + ", exception: " + e + ")");
            e.printStackTrace();
            return null;
        }
    }
    
    
    public Map<String, SyndFeed> retrieveFeeds(String[] sourcesUrl) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        try (CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build()) {
            HttpFeedFetcherThread[] threads = new HttpFeedFetcherThread[sourcesUrl.length];
            for (int i = 0; i < threads.length; i++) {
                HttpGet httpget = new HttpGet(sourcesUrl[i]);
                threads[i] = new HttpFeedFetcherThread(httpclient, httpget, i + 1);
            }
            // start the threads
            for (HttpFeedFetcherThread thread : threads) {
                thread.start();
            }
            // join the threads
            for (HttpFeedFetcherThread thread : threads) {
                thread.join();
            }
            // get feeds from threads
            Map<String, SyndFeed> syndFeedsMap = new HashMap<>();
            for (int i = 0; i < threads.length; i++) {
                String sourceUrl = sourcesUrl[i];
                HttpFeedFetcherThread thread = threads[i];
                if (thread.getSyndFeed() != null) {
                    syndFeedsMap.put(sourceUrl, thread.getSyndFeed());
                }
            }
            return syndFeedsMap;
        } catch (IOException | IndexOutOfBoundsException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
}
