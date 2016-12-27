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

    @RequestMapping(value = "/article/item/{pageName}", method = RequestMethod.GET)
    @Transactional
    public ModelAndView pageRead(@PathVariable("pageName") String pageName) {
        Page page = pageDao.findByName(pageName);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        if (page == null) {
            mav.addObject("message", "Page Not Found");
        } else {
            TextProcessor processor = BBProcessorFactory.getInstance().create();
            mav.addObject("displayName", page.getDisplayName());
            mav.addObject("content", processor.process(page.getContent()));
        }

        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return this.pageRead("Front_Page");
    }
}
