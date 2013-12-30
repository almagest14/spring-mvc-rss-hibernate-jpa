package kauppalehtiHarjoitus.rssHarjoitus.backend;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BackendController {

	@Autowired
	private FeedItemRepository feedItemRepository;
	
	@RequestMapping(value="/rssfeed", method = RequestMethod.GET)
	public ModelAndView getFeedInRss() {
		return dispatch("rssFeedViewer");
	}

	@RequestMapping(value="/rssfeedPage", method = RequestMethod.GET)
	public ModelAndView getFeedAsPage() {
		return dispatch("frontend/rssPageViewer");
	}
	
	@RequestMapping(value="/rssfeedPage", method = RequestMethod.POST)
	public ModelAndView getFeedAsPageRefresh() {
		this.initialize();
		return dispatch("frontend/rssPageViewer");
	}
	
	private ModelAndView dispatch(String view){

		List<FeedItem> items = feedItemRepository.findAll();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(view);
		mav.addObject("feedContent", items);
		return mav;	
	}	
	
	@PostConstruct	
	protected void initialize() {
		FeedItemHelper.initialize(feedItemRepository);		
	}
}