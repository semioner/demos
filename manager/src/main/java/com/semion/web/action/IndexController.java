package com.semion.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by heshuanxu on 2017/9/9.
 */
@Controller
public class IndexController {

       // @RequestMapping("/index.html")
        public ModelAndView index(){
            ModelAndView view = new ModelAndView("index");
            return view;
        }


}
