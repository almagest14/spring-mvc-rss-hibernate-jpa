package kauppalehtiHarjoitus.rssHarjoitus.backend;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class FeedItemRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public FeedItem save(FeedItem feedItem) {
		try {
			FeedItem existingItem =  entityManager.createNamedQuery(FeedItem.FIND_BY_URL, FeedItem.class)
													.setParameter("url", feedItem.getUrl())
													.getSingleResult();
			System.out.println("save ignored (duplicate)");
		} catch (PersistenceException e) {
			entityManager.persist(feedItem);			
		}
		return feedItem;
	}
	
	public List<FeedItem> findAll() {
		try {
			return entityManager.createNamedQuery(FeedItem.FIND_ALL, FeedItem.class)
					.getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}
}
