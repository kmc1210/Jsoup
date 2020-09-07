package com.covid19.app.coronaNews.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.covid19.app.coronaNews.model.service.CovidNewsService;
import com.covid19.app.coronaNews.model.service.CovidNewsServiceImpl;
import com.covid19.app.coronaNews.model.vo.CovidNews;

@Controller
public class CoronaNewsController {

	
	@Autowired
	private  CovidNewsService covidNewsService; 
	
	@RequestMapping(value="/covidNews.do", method = RequestMethod.GET)
	public ModelAndView newsList(@RequestParam(required=false, defaultValue = "1")int cPage) throws IOException {
		
		ModelAndView mav = new ModelAndView();
		int cntPerPage = 8;
		Map<String, Object> commandMap = covidNewsService.selectNewsList(cPage, cntPerPage);
		mav.addObject("paging", commandMap.get("paging"));
		mav.addObject("list", commandMap);
		mav.setViewName("newsBoard/news");
		
		return mav; 
	}
	
	
	
}
