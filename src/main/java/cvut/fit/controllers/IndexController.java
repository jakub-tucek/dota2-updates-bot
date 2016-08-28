package cvut.fit.controllers;

import cvut.fit.domain.entity.BlogUpdateEntry;
import cvut.fit.service.BlogParsingException;
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
            List<BlogUpdateEntry> blogUpdateEntries = blogService.downloadAllUpdates();
            System.out.println(blogUpdateEntries);
            model.addAttribute("blogUpdateEntries", blogUpdateEntries);
        } catch (BlogParsingException | IOException ex) {
            log.error(ex.toString());
        }
        return "index";
    }
}
