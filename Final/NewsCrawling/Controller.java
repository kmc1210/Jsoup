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
	public ModelAndView newsList(
			@RequestParam(required=false, defaultValue = "1")int cPage,
			@RequestParam(required=false, defaultValue = "t")String search_item,
			@RequestParam(required=false, defaultValue = "")String search_content) throws IOException {
		
		search_content = search_content.trim(); //공백제거
		ModelAndView mav = new ModelAndView();
		int cntPerPage = 8;
		Map<String, Object> commandMap = covidNewsService.selectNewsList(cPage, cntPerPage, search_item, search_content);
		mav.addObject("paging", commandMap.get("NewsPaging"));
		mav.addObject("list", commandMap);
		mav.setViewName("newsBoard/news");
		
		return mav; 
	}
	
	
	
}
