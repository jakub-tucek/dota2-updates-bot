package cvut.fit.service.blog.parser;

import cvut.fit.domain.entity.AbstractEntry;
import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.domain.repository.BlogEntryRepository;
import cvut.fit.domain.repository.BlogUpdateEntryRepository;
import cvut.fit.service.blog.BlogParsingException;
import cvut.fit.service.blog.DownloaderBlogConfig;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final BlogUpdateEntryRepository blogUpdateEntryRepository;

    private final BlogEntryRepository blogEntryRepository;


    @Autowired
    public BlogParser(BlogUpdateEntryRepository blogUpdateEntryRepository, BlogEntryRepository blogEntryRepository) {
        this.blogUpdateEntryRepository = blogUpdateEntryRepository;
        this.blogEntryRepository = blogEntryRepository;
    }

    /**
     * Parse update page starter.
     *
     * @param html
     * @return
     * @throws NumberFormatException
     * @throws BlogParsingException
     */
    public List<BlogUpdateEntry> parseUpdatePage(Document html) throws NumberFormatException, BlogParsingException {
        List<BlogUpdateEntry> basicBlogEntryListPage = new ArrayList<>();
        Elements posts = html.select("div[id^=post]");
        for (Element entry : posts) {
            BlogUpdateEntry blogUpdateEntry = (BlogUpdateEntry) parseBasic(entry, true);
            List<BlogUpdateEntry> bl = blogUpdateEntryRepository.findByValveId(blogUpdateEntry.getValveId());

            if (bl.size() != 0) {
                log.info("Found collision in valve id(update). Post is already saved - valveId=" + blogUpdateEntry.getValveId());
                break;
            }
            basicBlogEntryListPage.add(blogUpdateEntry);
        }

        return basicBlogEntryListPage;
    }

    /**
     * Parse blog starter.
     *
     * @param html
     * @return
     */
    public List<BlogEntry> parseBlogPage(Document html) throws NumberFormatException, BlogParsingException {
        List<BlogEntry> blogEntryListPage = new ArrayList<>();
        Elements posts = html.select("div[id^=post]");
        for (Element entry : posts) {
            BlogEntry blogEntry = (BlogEntry) parseBasic(entry, false);
            parseBlogEntry(entry, blogEntry);
            List<BlogEntry> bl = blogEntryRepository.findByValveId(blogEntry.getValveId());

            if (bl.size() != 0) {
                log.info("Found collision in valve id(blog). Post is already saved - valveId=" + blogEntry.getValveId());
                break;
            }
            blogEntryListPage.add(blogEntry);
        }
        return blogEntryListPage;
    }

    private AbstractEntry parseBasic(Element entry, boolean isBlogUpdate) throws NumberFormatException, BlogParsingException {
        int valveId = parseValveId(entry);
        String title = parseTitle(entry);

        String[] postDateAuthorPartSplit = parsePostDateAuthor(entry);
        LocalDateTime postDate = parsePostDateString(postDateAuthorPartSplit[0]);
        String author = parseAuthorString(postDateAuthorPartSplit[1]);

        String content = parseContent(entry);

        String url = parseUrl(entry);

        return new BlogUpdateEntry(title, author, url, content, postDate, valveId);
    }

    private void parseBlogEntry(Element entry, BlogEntry blogEntry) throws NumberFormatException, BlogParsingException {
        blogEntry.setUrl(parseUrl(entry));

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

    private LocalDateTime parsePostDateString(String postDateString) throws BlogParsingException {
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
