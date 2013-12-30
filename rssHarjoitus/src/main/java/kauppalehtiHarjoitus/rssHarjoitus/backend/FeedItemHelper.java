package kauppalehtiHarjoitus.rssHarjoitus.backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedItemHelper {

	public static void initialize(FeedItemRepository feedItemRepository){
		fetchFeed(feedItemRepository,"http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp");
		fetchFeed(feedItemRepository,"http://rss.kauppalehti.fi/rss/yritysuutiset.jsp");
		fetchFeed(feedItemRepository,"http://rss.kauppalehti.fi/rss/omaraha.jsp");
		fetchFeed(feedItemRepository,"http://keskustelu.kauppalehti.fi/5/i/keskustelu/rss/rssthreads.jspa?forumID=32&numItems=30");
	}
	
	private static void fetchFeed(FeedItemRepository feedItemRepository, String urlStr){
		
		try {
			URL url  = new URL(urlStr);
			XmlReader reader = null;
			 
			try {			  
				  reader = new XmlReader(url);
				  SyndFeed feed = new SyndFeedInput().build(reader);			 
				  
				  for (@SuppressWarnings("rawtypes")
				 	Iterator i = feed.getEntries().iterator(); i.hasNext();) {
				    SyndEntry entry = (SyndEntry)i.next();
				    
				    System.out.println(entry.getTitle());
				    
				    FeedItem feedItem  = new FeedItem();
				    
				    feedItem.setTitle(entry.getTitle());
				    feedItem.setUrl(entry.getLink());
				    feedItem.setSummary(entry.getDescription()!=null ? ""+entry.getDescription().getValue() : "");
				    feedItem.setCreatedDate(entry.getPublishedDate());
				    feedItemRepository.save(feedItem);
				  }
			} finally {
			    if (reader != null)
			        reader.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
