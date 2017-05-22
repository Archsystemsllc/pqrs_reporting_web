package com.archsystemsinc.pqrs.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

//	@RequestMapping("/")
//    public String index(HttpServletRequest request, Principal currentUser, Model model) {
//        return "index";
//    }
	
	@RequestMapping("/states")
    public String states(HttpServletRequest request, Principal currentUser, Model model) {
		model.addAttribute("areaType", "URBAN");
        return "states";
    }
	
	@RequestMapping("/zipcodes")
    public String zipcodes(HttpServletRequest request, Principal currentUser, Model model) {
        return "zipcodes";
    }

}
