package com.weltcrawler.infrastructure;

import java.io.*;
import java.net.*;
import java.util.*;

import com.weltcrawler.domain.Article;

public class RssReader {

	/** parses rss feed and returns is as a list of articles */
	public List<Article> fetchArticles(final String urlAddress) {
		try {
			// holds all the articles we fetched from rss
			List<Article> articles = new ArrayList<Article>();

			// fetch the rss fedd
			final URL rssUrl = new URL(urlAddress);
			final BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));

			// extract the relevant data (title)
			String line;
			while ((line = in.readLine()) != null) {
				String title = "";

				if (line.contains("<title>")) {
					final int firstPos = line.indexOf("<title>");
					title = line.substring(firstPos);
					title = title.replace("<title>", "");
					final int lastPos = title.indexOf("</title>");
					title = title.substring(0, lastPos);

					// construct the article
					Article article = new Article(title, "");

					// add the article to the final result, which will be returns
					articles.add(article);
				}
			}
			in.close();
			return articles;

		} catch (final MalformedURLException ue) {
			System.out.println("Malformed URL");
		} catch (final IOException ioe) {
			System.out.println("Something wrong");
		}
		return null;
	}
}
