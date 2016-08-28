package cvut.fit.service;

import cvut.fit.domain.entity.BlogUpdateEntry;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */

@Service
public class BlogParser {
    private static final Logger log = LoggerFactory.getLogger(BlogParser.class);

    public BlogParser() {
    }

    public List<BlogUpdateEntry> parseUpdatePage(Document html) {
        List<BlogUpdateEntry> blogUpdateEntryListPage = new ArrayList<>();

        Elements mainDivs = html.select("div[id^=post]");

       // log.info(mainDivs.toString());

        mainDivs.forEach(mainDiv -> blogUpdateEntryListPage.add(parserUpdateEntry(mainDiv)));


        return blogUpdateEntryListPage;
    }

    public BlogUpdateEntry parserUpdateEntry(Element el) {


        return null;
    }
}
