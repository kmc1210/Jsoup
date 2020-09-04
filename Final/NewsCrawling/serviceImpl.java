package com.covid19.app.coronaNews.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covid19.app.coronaNews.model.dao.CovidNewsDao;
import com.covid19.app.coronaNews.model.vo.CovidNews;

@Service
public class CovidNewsServiceImpl implements CovidNewsService{

	@Autowired
	private CovidNewsDao covidNewsDao;
	
	private static String COVID_NEWS_URL = "https://news.sbs.co.kr/news/newsHotIssueList.do?tagId=10000050973";
	
	//시작하자마자 메소드를 실행하게 해주는 어노테이션
	@PostConstruct
	public List<CovidNews> getCovidNews() throws IOException {
		
		List<CovidNews> newsList = new ArrayList<>();
		Document doc = Jsoup.connect(COVID_NEWS_URL).get();
//		Elements contents = doc.select("#container > div > div.w_news_list.type_issue > ul > li:nth-child(1) > a.news > p > strong");
		Elements contents = doc.select("#container > div > div.w_news_list.type_issue > ul > li > a.news");
		
		for(Element content : contents) {
			
			CovidNews covidnews = new CovidNews();
			covidnews.setNewsTitle(content.select("strong.sub").html()); //뉴스제목
			covidnews.setThumbNail(content.select("span.thumb").html()); //썸네일
			covidnews.setNewsContents(content.select("span.read").text()); //뉴스 간략내용
			covidnews.setLink(content.select("a.news").attr("href")); //뉴스 링크
			covidnews.setRegDate(content.select("span.date").text()); //작성일
			covidnews.setCompany(1); //SBS는 Company 1번
			newsList.add(covidnews);
			System.out.println(newsList);
			
				covidNewsDao.insertCovidNews(covidnews);
				//크롤링 할때마다 DB에 넣어주기
				covidNewsDao.deleteCovidNews(covidnews.getNews_idx());
//				//크롤링 할때마다 DB에 중복값 지워주기
			
//			System.out.println(covidnews.toString());
			
			
		}
		
		
		return newsList;
		
		
		
		
	}
	
	
	
	
}
