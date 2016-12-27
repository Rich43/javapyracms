package com.pynguins.controllers;

import com.pynguins.models.Page;
import com.pynguins.models.PageDao;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PageController {

    @Autowired
    private PageDao pageDao;

    public ModelAndView makeMav(String content, String displayName) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        TextProcessor processor = BBProcessorFactory.getInstance().create();
        mav.addObject("content", processor.process(content));
        mav.addObject("displayName", displayName);
        return mav;
    }

    @RequestMapping(value = "/article/item/{pageName}", method = RequestMethod.GET)
    @Transactional
    public ModelAndView pageRead(@PathVariable("pageName") String pageName) {
        Page page = pageDao.findByName(pageName);
        if (page == null) {
            return this.makeMav("Page Not Found", "Error");
        } else {
            return this.makeMav(page.getContent(), page.getDisplayName());
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return this.pageRead("Front_Page");
    }

    @RequestMapping(value = "/article/list", method = RequestMethod.GET)
    public ModelAndView list() {
        Iterable<Page> pages = pageDao.findAll();
        String result = "";
        for (Page page : pages) {
            result += String.format("[url=/article/item/%s]%s[/url]\n",
                    page.getName(), page.getDisplayName());
        }
        return this.makeMav(result, "List Articles");
    }
}
