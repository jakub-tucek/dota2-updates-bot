package cvut.fit.service;

import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.domain.repository.BlogUpdateEntryRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */

@Service
public class BlogParser {
    private static final Logger log = LoggerFactory.getLogger(BlogParser.class);

    final
    BlogUpdateEntryRepository blogUpdateEntryRepository;

    @Autowired
    public BlogParser(BlogUpdateEntryRepository blogUpdateEntryRepository) {
        this.blogUpdateEntryRepository = blogUpdateEntryRepository;
    }

    public List<BlogUpdateEntry> parseUpdatePage(Document html) throws NumberFormatException, BlogParsingException {
        List<BlogUpdateEntry> blogUpdateEntryListPage = new ArrayList<>();

        Elements mainDiv = html.select("div[id^=post]");

        for (Element entry : mainDiv) {
            BlogUpdateEntry blogUpdateEntry = parseUpdateEntry(entry);
            List<BlogUpdateEntry> bl = blogUpdateEntryRepository.findByValveId(blogUpdateEntry.getValveId());


            if (bl.size() != 0) {
                log.info("Found collision in valve id");
                break;
            }

            blogUpdateEntryListPage.add(parseUpdateEntry(entry));
            blogUpdateEntryRepository.save(blogUpdateEntry);
        }

        return blogUpdateEntryListPage;
    }


    private BlogUpdateEntry parseUpdateEntry(Element el) throws NumberFormatException, BlogParsingException {
        int valveId = parseValveId(el);
        String title = parseTitle(el);
        LocalDate postDate = parsePostDate(el);
        String content = parseContent(el);

        return new BlogUpdateEntry(valveId, title, content, postDate);
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
        return Integer.parseInt(valveIdString.substring(BlogConfig.ENTRY_PREFIX_ID.length() + 1));
    }

    /**
     * Parse title part.
     *
     * @param entry
     * @return
     * @throws BlogParsingException
     */
    private String parseTitle(Element entry) throws BlogParsingException {
        Elements titleEl = entry.select(BlogConfig.TITLE_SELECTOR);
        if (titleEl == null) throw new BlogParsingException("Title class missing");
        if (titleEl.size() > 1) throw new BlogParsingException("Too many titles");

        Elements elTitleLink = titleEl.select("a");

        if (elTitleLink == null) throw new BlogParsingException("Title class (link) missing");
        if (elTitleLink.size() > 1) throw new BlogParsingException("Too many title classes (link)");

        return elTitleLink.html();
    }


    /**
     * Parse posted date.
     *
     * @param entry
     * @return
     * @throws BlogParsingException
     */
    private LocalDate parsePostDate(Element entry) throws BlogParsingException {
        Elements postDateEl = entry.select(BlogConfig.POST_DATE_SELECTOR);
        if (postDateEl == null) throw new BlogParsingException("Date class missing");
        if (postDateEl.size() > 1) throw new BlogParsingException("Too many date classes");


        String dateString = postDateEl.html();
        String[] dateStringSplit = dateString.split("\\s-+");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BlogConfig.DATE_FORMAT, Locale.ENGLISH);

        if (dateStringSplit.length < 1) throw new BlogParsingException("Invalid date");

        try {
            return LocalDate.parse(dateStringSplit[0], formatter);
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
        Elements contentEl = entry.select(BlogConfig.CONTENT_SELECTOR);
        if (contentEl == null) throw new BlogParsingException("Content class missing");
        if (contentEl.size() > 1) throw new BlogParsingException("Too many content classes");

        return contentEl.html().replaceAll("\\<.*?>", "");
    }

}
