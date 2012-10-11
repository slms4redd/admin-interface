/*
 * nfms4redd Portal Interface - http://nfms4redd.org/
 *
 * (C) 2012, FAO Forestry Department (http://www.fao.org/forestry/)
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation;
 * version 3.0 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package org.fao.unredd;

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.UNREDDGeostoreManager;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {
    
	private static Logger logger = Logger.getLogger(ApplicationController.class);
	
    @Autowired
    GeoStoreClient client;
    
    //@Autowired
	//org.fao.unredd.portal.Config config;
        
    //@Autowired
    //net.tanesha.recaptcha.ReCaptchaImpl reCaptcha;
    
    UNREDDGeostoreManager geostore = null;

    
    @RequestMapping(value="/feedbacks", method = RequestMethod.GET)
    public @ResponseBody ModelAndView feedbacks() {
        return new ModelAndView("messages", "messages", "test");
    }
       
}
