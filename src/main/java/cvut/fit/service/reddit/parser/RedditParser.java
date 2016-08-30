package cvut.fit.service.reddit.parser;

import cvut.fit.domain.entity.RedditEntry;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Service
public class RedditParser {
    public List<RedditEntry> parseUserSubmittedPage(Document html) {
        return null;
    }


}
