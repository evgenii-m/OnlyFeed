/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author push
 * 
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static Logger logger = LogManager.getLogger(IndexController.class);
    
    @RequestMapping(method = GET)
    public String index(Model uiModel) {
        return "index";
    }
    
    @RequestMapping(value="/loginfail")
    public String loginFail() {
        logger.info("Login fail");
        return "index";
    }
    
}
