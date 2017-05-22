package com.archsystemsinc.pqrs.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

//	@RequestMapping("/")
//    public String index(HttpServletRequest request, Principal currentUser, Model model) {
//        return "index";
//    }
	
	@RequestMapping("/states/dataset/{dataset}")
    public String states(@PathVariable("dataset") String dataset, @RequestParam(value = "filter") String filter, HttpServletRequest request, Principal currentUser, Model model) {
		model.addAttribute("filter", filter);
		model.addAttribute("dataset", dataset);
        return "states";
    }
	
	@RequestMapping("/zipcodes/dataset/{dataset}/states/{state}")
    public String zipcodes(@PathVariable("dataset") String dataset, @RequestParam(value = "filter") String filter, @PathVariable("state") String state, HttpServletRequest request, Principal currentUser, Model model) {
		model.addAttribute("filter", filter);
		model.addAttribute("state", state);
		model.addAttribute("dataset", dataset);
        return "zipcodes";
    }

}
