package cvut.fit.controllers;

import cvut.fit.domain.entity.BlogEntry;
import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.service.parser.BlogParsingException;
import cvut.fit.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jakub Tuček on 28.8.2016.
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final BlogService blogService;

    @Autowired
    public IndexController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        try {
            List<BlogUpdateEntry> blogUpdateEntries = blogService.downloadAllBlogUpdates();
            model.addAttribute("blogUpdateEntries", blogUpdateEntries);

            List<BlogEntry> blogEntries = blogService.downloadAllBlog();
            model.addAttribute("blogEntries", blogEntries);

        } catch (BlogParsingException | IOException ex) {
            log.error(ex.toString());
        }
        return "index";
    }

    @RequestMapping("/updates")
    public String updates(Model model) {
        try {

            Iterable<BlogUpdateEntry> blogUpdateEntries = blogService.getAllBlogUpdates();
            model.addAttribute("blogUpdateEntries", blogUpdateEntries);
        } catch (BlogParsingException | IOException ex) {
            log.error(ex.toString());
        }
        return "index";
    }

    @RequestMapping("/blog")
    public String blog(Model model) {
        try {
            Iterable<BlogEntry> blogEntries = blogService.getAllBlog();
            model.addAttribute("blogEntries", blogEntries);
        } catch (BlogParsingException | IOException ex) {
            log.error(ex.toString());
        }
        return "index";
    }
}
