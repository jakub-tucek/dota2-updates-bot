package cvut.fit.service.reddit;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.RedditEntry;
import cvut.fit.domain.repository.RedditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Service
public class DownloaderRedditService {

    private final RedditRepository redditRepository;

    @Autowired
    public DownloaderRedditService(RedditRepository redditRepository) {
        this.redditRepository = redditRepository;
    }

    public List<RedditEntry> downloadSirBelvedere() {


        return null;
    }


    public Iterable<BlogEntry> getAllSirBelvedere() {
        return redditRepository.findByAuthor("SirBelvedere");
    }
}
