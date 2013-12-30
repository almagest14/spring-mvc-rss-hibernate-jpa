package kauppalehtiHarjoitus.rssHarjoitus.backend;

import java.util.Date;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "feeditem")
@NamedQueries({
 @NamedQuery(name = FeedItem.FIND_ALL, query = "select a from FeedItem a order by a.createdDate DESC"),
 @NamedQuery(name = FeedItem.FIND_BY_URL, query = "select a from FeedItem a where a.url = :url")
})
public class FeedItem implements java.io.Serializable {

	public static final String FIND_ALL = "FeedItem.findAll";
	
	public static final String FIND_BY_URL = "FeedItem.findByUrl";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 10000, name = "title")
	String title;
	
	@Column(unique = true, length = 10000, name = "url")
	String url;
	
	@Column(length = 10000, name = "summary")
	String summary;
	
	@Column(length = 10000, name = "createdDate")
	Date createdDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
