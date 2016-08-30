package cvut.fit.service.reddit.parser;

import cvut.fit.domain.entity.RedditEntry;
import cvut.fit.domain.repository.RedditEntryRepository;
import cvut.fit.service.reddit.DownloaderRedditConfig;
import cvut.fit.service.reddit.RedditParsingException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Jakub Tuček on 30.8.2016.
 */
@Service
public class RedditParser {
    private static final Logger log = LoggerFactory.getLogger(RedditParser.class);


    private final RedditEntryRepository redditEntryRepository;

    @Autowired
    public RedditParser(RedditEntryRepository redditEntryRepository) {
        this.redditEntryRepository = redditEntryRepository;
    }


    public Elements parseUserSubmittedPage(Document html) {
        return html.select("div[class^=entry]");
    }

    public RedditEntry parseUserSubmittedEntry(Element entry) throws RedditParsingException {
        String url = parseUrl(entry);
        System.out.println(url);

        String title = parseTitle(entry);
        System.out.println(title);

        String author = parseAuthor(entry);
        System.out.println(author);

        //0: created, 1: edited
        LocalDateTime[] timestamps = parseTimestamps(entry);
        System.out.println(Arrays.toString(timestamps));

        String subReddit = parseSubReddit(entry);
        System.out.println(subReddit);

        if (timestamps[1] != null) {
            return new RedditEntry(url, author, url, "", timestamps[0], timestamps[1], subReddit);
        } else {
            return new RedditEntry(url, author, url, "", timestamps[0], subReddit);
        }
    }

    private String parseUrl(Element entry) throws RedditParsingException {
        Elements elLink = entry.select(DownloaderRedditConfig.URL_SELECTOR);
        if (elLink == null) throw new RedditParsingException("Url of reddit post not found");
        if (elLink.size() > 1) throw new RedditParsingException("Too many url reddit post");

        String link = elLink.attr("href");
        if (link == null) throw new RedditParsingException("Url of reddit post not found");

        return "https://www.reddit.com" + link;
    }

    private String parseTitle(Element entry) throws RedditParsingException {
        Elements elLink = entry.select(DownloaderRedditConfig.URL_SELECTOR);
        if (elLink == null) throw new RedditParsingException("Title of reddit post not found");
        if (elLink.size() > 1) throw new RedditParsingException("Too many of title reddit post");

        String title = elLink.html();
        if (title == null) throw new RedditParsingException("Title of reddit post not found");

        return title;
    }

    private String parseAuthor(Element entry) throws RedditParsingException {
        Elements author = entry.select(DownloaderRedditConfig.AUTHOR_SELECTOR);
        if (author == null) throw new RedditParsingException("author of reddit post not found");
        if (author.size() > 1) throw new RedditParsingException("Too many authors of reddit post");

        return author.html();
    }

    private LocalDateTime[] parseTimestamps(Element entry) throws RedditParsingException {
        Elements timestamps = entry.select(DownloaderRedditConfig.TIMESTAMPS_SELECTOR);
        if (timestamps.size() < 1 || timestamps.size() > 2) throw new RedditParsingException("Timestamps not found");

        LocalDateTime[] x = new LocalDateTime[2];
        if (timestamps.size() == 2) {
            x[1] = getEditedTimestamp(timestamps.get(1));
        } else {
            x[1] = null;
        }

        x[0] = getCreatedTimestamp(timestamps.get(0));

        return x;
    }

    private LocalDateTime getCreatedTimestamp(Element time) throws RedditParsingException {
        String created = time.attr("datetime");
        if (created == null) throw new RedditParsingException("Created timestamp not found");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DownloaderRedditConfig.DATE_FORMAT, Locale.ENGLISH);
        return LocalDateTime.parse(created, formatter);
    }

    private LocalDateTime getEditedTimestamp(Element time) throws RedditParsingException {
        String edited = time.attr("datetime");
        if (edited == null) throw new RedditParsingException("Edited timestamp not found");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DownloaderRedditConfig.DATE_FORMAT, Locale.ENGLISH);
        return LocalDateTime.parse(edited, formatter);
    }

    private String parseSubReddit(Element entry) throws RedditParsingException {
        Elements subredditEl = entry.select(DownloaderRedditConfig.SUBREDDIT_SELECTOR);
        if (subredditEl == null) throw new RedditParsingException("Subreddit mark not found.");
        if (subredditEl.size() > 1) throw new RedditParsingException("Too many subreddit marks.");

        Elements subredditEl1 = subredditEl.select("a");

        String title = subredditEl1.attr("href");
        if (title == null) throw new RedditParsingException("Subreddit mark not found");

        return title;
    }

    /**
     * Parsing content of entry. Result saved to reddit entry entity.
     *
     * @param htmlPost
     * @param redditEntry
     * @throws RedditParsingException
     */
    public void parseEntryDetailContent(Document htmlPost, RedditEntry redditEntry) throws RedditParsingException {
        Elements mainDiv = htmlPost.select(DownloaderRedditConfig.CONTENT_MAIN_DIV_SELECTOR);
        if (mainDiv == null) throw new RedditParsingException("Content main div not found.");
        if (mainDiv.size() > 1) throw new RedditParsingException("Too many content main divs.");

        Elements innerDiv = mainDiv.select(DownloaderRedditConfig.CONTENT_INNER_DIV_SELECTOR);

        if (innerDiv == null) throw new RedditParsingException("Content inner div not found.");
        if (innerDiv.size() > 1) throw new RedditParsingException("Too many content inner divs.");

        redditEntry.setContent(innerDiv.html());
    }

}
