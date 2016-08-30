package cvut.fit.service.reddit;

import cvut.fit.domain.entity.RedditEntry;
import cvut.fit.domain.repository.RedditEntryRepository;
import cvut.fit.service.DownloaderConfig;
import cvut.fit.service.reddit.parser.RedditParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Service
public class DownloaderRedditService {

    private final RedditEntryRepository redditEntryRepository;

    private final RedditParser redditParser;

    @Autowired
    public DownloaderRedditService(RedditEntryRepository redditEntryRepository, RedditParser redditParser) {
        this.redditEntryRepository = redditEntryRepository;
        this.redditParser = redditParser;
    }

    public List<RedditEntry> downloadSirBelvedere() throws IOException, RedditParsingException {
        List<RedditEntry> redditEntryList = new ArrayList<>();

        for (int i = 1; i <= DownloaderRedditConfig.REDDIT_MAX_PAGES; i++) {

//          setProxy();

            Document html = Jsoup.connect(DownloaderRedditConfig.REDDIT_SIR_BELVEDERE_URL + i).userAgent(DownloaderConfig.USER_AGENT).header("Accept-Language", DownloaderConfig.HEADER_ACCEPT_LANG).get();

            List<RedditEntry> redditEntryListPage = redditParser.parseUserSubmittedPage(html);

            if (redditEntryListPage.size() == 0) break;

            redditEntryList.addAll(redditEntryListPage);
        }

        for (int i = redditEntryList.size() - 1; i >= 0; i--) {
            redditEntryRepository.save(redditEntryList.get(i));
        }
        return redditEntryList;

    }


    public Iterable<RedditEntry> getAllSirBelvedere() {
        return redditEntryRepository.findByAuthor("SirBelvedere");
    }
}
