package com.crawler.webCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;

public class Crawler {
	private HashSet<String> urlLink;

	public Crawler() {
		urlLink = new HashSet<String>();
	}

	public void getPageLinks(String URL, String searchText, int level) {

		if (!urlLink.contains(URL) && level > 0) {
			try {
				if (urlLink.add(URL)) {
					level--;
				}
				try {
					Document doc = Jsoup.connect(URL).get();
					if (doc.select("div:contains(" + searchText + ")").size() > 0
							&& null != doc.select("div:contains(" + searchText + ")").get(1)) {
						System.out.println(URL);
					}
					Elements availableLinksOnPage = doc.select("a[href]");
					for (Element ele : availableLinksOnPage) {
						getPageLinks(ele.attr("abs:href"), searchText, level);
					}
				} catch (IllegalArgumentException e) {
					// System.out.println("Invalid URL");
				}
			} catch (IOException e) {
				// System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		Crawler obj = new Crawler();
		// arguments : URLs, search text, iteration level
		String[] urls = { "https://spring.io/" };
		String searchText = "Applications";
		int iterationlevel = 3;
		for (String url : urls) {
			System.out.println("Search result(s) for URL >> " + url + " -> contains : " + searchText);
			obj.getPageLinks(url, searchText, iterationlevel);
		}
		System.out.println("Search Done!");
	}
}
