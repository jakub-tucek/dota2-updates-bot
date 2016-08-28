package cvut.fit.service;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Service
public class BlogService {
    private static final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogParser blogParser;

    @Autowired
    public BlogService(BlogParser blogParser) {
        this.blogParser = blogParser;
    }


    public List<BlogUpdateEntry> downloadAllUpdates() throws IOException, BlogParsingException {
        List<BlogUpdateEntry> blogEntryList = new ArrayList<>();

        for (int i = 1; i <= BlogConfig.BLOG_UPDATE_MAX_PAGES; i++) {
            Document html = Jsoup.connect(BlogConfig.BLOG_UPDATE_URL + i).userAgent(BlogConfig.USER_AGENT).header("Accept-Language", BlogConfig.HEADER_ACCEPT_LANG).get();

            List<BlogUpdateEntry> blogUpdateEntryListPage = blogParser.parseUpdatePage(html);

            if (blogUpdateEntryListPage.size() == 0) break;

            blogEntryList.addAll(blogUpdateEntryListPage);
        }

        return blogEntryList;
    }

    public List<BlogEntry> downloadAllBlogs() throws IOException, BlogParsingException {
        List<BlogEntry> blogEntryList = new ArrayList<>();

        for (int i = 1; i <= BlogConfig.BLOG_UPDATE_MAX_PAGES; i++) {
            Document html = Jsoup.connect(BlogConfig.BLOG_UPDATE_URL + i).userAgent(BlogConfig.USER_AGENT).header("Accept-Language", BlogConfig.HEADER_ACCEPT_LANG).get();

            List<BlogEntry> blogEntryListPage = blogParser.parseBlogPage(html);

            if (blogEntryListPage.size() == 0) break;

            blogEntryList.addAll(blogEntryListPage);
        }

        return blogEntryList;
    }
}
