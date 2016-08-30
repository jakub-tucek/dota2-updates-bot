package cvut.fit.service.reddit;

import cvut.fit.domain.entity.RedditEntry;
import cvut.fit.domain.repository.RedditEntryRepository;
import cvut.fit.service.DownloaderConfig;
import cvut.fit.service.reddit.parser.RedditParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Service
public class DownloaderRedditService {
    private static final Logger log = LoggerFactory.getLogger(DownloaderRedditService.class);

    private final RedditEntryRepository redditEntryRepository;

    private final RedditParser redditParser;

    @Autowired
    public DownloaderRedditService(RedditEntryRepository redditEntryRepository, RedditParser redditParser) {
        this.redditEntryRepository = redditEntryRepository;
        this.redditParser = redditParser;
    }

    public List<RedditEntry> downloadSirBelvedere() throws IOException, RedditParsingException {
        List<RedditEntry> redditEntryList = new ArrayList<>();
        boolean collisionFound = false;
//          setProxy();

        //Iteration over pages
        for (int i = 1; i <= DownloaderRedditConfig.REDDIT_MAX_PAGES; i++) {
            Document html = Jsoup.connect(DownloaderRedditConfig.REDDIT_SIR_BELVEDERE_URL + i).userAgent(DownloaderConfig.USER_AGENT).header("Accept-Language", DownloaderConfig.HEADER_ACCEPT_LANG).get();

            Elements posts = redditParser.parseUserSubmittedPage(html);
            //Iteration over entry on page
            for (Element entry : posts) {
                RedditEntry redditEntry = redditParser.parseUserSubmittedEntry(entry);
                Document htmlPost = Jsoup.connect(redditEntry.getUrl()).userAgent(DownloaderConfig.USER_AGENT).header("Accept-Language", DownloaderConfig.HEADER_ACCEPT_LANG).get();
                redditParser.parseEntryDetailContent(htmlPost, redditEntry);
                List<RedditEntry> r1 = redditEntryRepository.findByUrl(redditEntry.getUrl());

                if (isCollisionFound(redditEntry, r1)) {
                    collisionFound = true;
                    break;
                }
                redditEntryList.add(redditEntry);
            }
            if (collisionFound) break;

        }
        for (int i = redditEntryList.size() - 1; i >= 0; i--) {
            redditEntryRepository.save(redditEntryList.get(i));
        }
        return redditEntryList;
    }

    private boolean isCollisionFound(RedditEntry redditEntry, List<RedditEntry> r1EntryDatabase) throws RedditParsingException {
        if (r1EntryDatabase.size() != 0) {
            log.info("Found collision in url of reddit post. Post is already saved.Url: " + redditEntry.getUrl());
            //URL already in DB
            if (r1EntryDatabase.size() != 0) {
                if (r1EntryDatabase.size() > 1) {
                    //URL IS NOT UNIQUE
                    throw new RedditParsingException("Url is not unique in database.Url: " + redditEntry.getUrl());
                }
                if (redditEntry.getEdited().equals(r1EntryDatabase.get(0).getEdited())) {
                    return true;
                } else {
                    // Content was changed
                    redditEntry = r1EntryDatabase.get(0);
                    redditEntry.setEdited(LocalDateTime.now());
                }
            }
        }
        return false;
    }

    public Iterable<RedditEntry> getAllSirBelvedere() {
        return redditEntryRepository.findByAuthor("SirBelvedere");
    }
}
