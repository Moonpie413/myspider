package org.wxh.html.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class JsoupParser implements IParser {

    private Logger logger = Logger.getLogger(this.getClass());
    private String charset = "UTF-8";

    @Override
    public Set<String> getAllLinks(String filePath, String baseUri) throws IOException {
        Set<String> urlSet = new HashSet<>();
        Document document = Jsoup.parse(new File(filePath), charset);
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            urlSet.add(linkHref);
        }
        return urlSet;
    }

    @Override
    public Set<String> getAllLinks(InputStream inputStream, String baseUri) throws IOException {
        Set<String> urlSet = new HashSet<>();
        Document document = Jsoup.parse(inputStream, charset, baseUri);
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            urlSet.add(linkHref);
        }
        return urlSet;
    }

    @Override
    public Set<String> getHTMLLinks(InputStream inputStream) {
        return null;
    }

    @Override
    public Set<String> getIMGLinks(InputStream inputStream) {
        return null;
    }
}
