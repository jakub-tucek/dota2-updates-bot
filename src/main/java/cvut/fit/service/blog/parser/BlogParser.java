package cvut.fit.service.blog.parser;

import cvut.fit.domain.entity.AbstractEntry;
import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.service.blog.BlogParsingException;
import cvut.fit.service.blog.DownloaderBlogConfig;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */

@Service
public class BlogParser {
    private static final Logger log = LoggerFactory.getLogger(BlogParser.class);

    /**
     * Returns page posts div.
     *
     * @param html
     * @return
     * @throws NumberFormatException
     * @throws BlogParsingException
     */
    public Elements parsePage(Document html) throws NumberFormatException, BlogParsingException {
        List<BlogUpdateEntry> basicBlogEntryListPage = new ArrayList<>();
        return html.select("div[id^=post]");
    }

    public AbstractEntry parseBlogEntry(Element entry, boolean isBlogEntry) throws NumberFormatException, BlogParsingException {
        int valveId = parseValveId(entry);
        String title = parseTitle(entry);

        String[] postDateAuthorPartSplit = parsePostDateAuthor(entry);
        LocalDateTime postDate = parsePostDate(postDateAuthorPartSplit[0]);
        String author = parseAuthorString(postDateAuthorPartSplit[1]);

        String content = parseContent(entry);

        String url = parseUrl(entry);

        if (isBlogEntry) {
            return new BlogEntry(title, author, url, content, postDate, valveId);
        } else {
            return new BlogUpdateEntry(title, author, url, content, postDate, valveId);
        }
    }

    private String parseUrl(Element entry) {
        Elements titleEl = entry.select(DownloaderBlogConfig.TITLE_SELECTOR);
        Elements elTitleLink = titleEl.select("a");

        return elTitleLink.attr("href");
    }

    /**
     * Parse valve internal entry id.
     *
     * @param entry
     * @return
     * @throws NumberFormatException
     */
    private int parseValveId(Element entry) throws NumberFormatException {
        String valveIdString = entry.attributes().get("id");
        return Integer.parseInt(valveIdString.substring(DownloaderBlogConfig.ENTRY_PREFIX_ID.length()));
    }

    /**
     * Parse title part.
     *
     * @param entry
     * @return
     * @throws BlogParsingException
     */
    private String parseTitle(Element entry) throws BlogParsingException {
        Elements titleEl = entry.select(DownloaderBlogConfig.TITLE_SELECTOR);
        if (titleEl == null) throw new BlogParsingException("Title class missing");
        if (titleEl.size() > 1) throw new BlogParsingException("Too many titles");

        Elements elTitleLink = titleEl.select("a");

        if (elTitleLink == null) throw new BlogParsingException("Title class (link) missing");
        if (elTitleLink.size() > 1) throw new BlogParsingException("Too many title classes (link)");

        return elTitleLink.html();
    }

    /**
     * Parse part where date and author is.
     *
     * @param entry
     * @return dat
     * @throws BlogParsingException
     */

    private String[] parsePostDateAuthor(Element entry) throws BlogParsingException {
        Elements postDateEl = entry.select(DownloaderBlogConfig.POST_DATE_AUTHOR_SELECTOR);
        if (postDateEl == null) throw new BlogParsingException("Date class missing");
        if (postDateEl.size() > 1) throw new BlogParsingException("Too many date classes");


        String dateString = postDateEl.html();
        String[] dateStringSplit = dateString.split("\\s-+");
        if (dateStringSplit.length != 2) throw new BlogParsingException("Invalid date");

        return dateStringSplit;
    }

    private String parseAuthorString(String author) {
        return author.substring(DownloaderBlogConfig.AUTHOR_OFFSET);
    }

    private LocalDateTime parsePostDate(String postDateString) throws BlogParsingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DownloaderBlogConfig.DATE_FORMAT, Locale.ENGLISH);
        try {
            return LocalDateTime.from(LocalDate.parse(postDateString, formatter).atStartOfDay());
        } catch (DateTimeParseException ex) {
            throw new BlogParsingException("Invalid date(" + ex + ")");
        }
    }

    /**
     * Parse content and removing html tags.
     *
     * @param entry
     * @return
     * @throws BlogParsingException
     */
    private String parseContent(Element entry) throws BlogParsingException {
        Elements contentEl = entry.select(DownloaderBlogConfig.CONTENT_SELECTOR);
        if (contentEl == null) throw new BlogParsingException("Content class missing");
        if (contentEl.size() > 1) throw new BlogParsingException("Too many content classes");

        // return contentEl.html().replaceAll("\\<.*?>", ""); //removing html tags
        return contentEl.html();
    }


}
