package org.wxh.html.parser;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.wxh.httphandle.IFileDownload;
import org.wxh.httphandle.LocalFileDownload;

import java.util.Set;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class JsoupParserImplTest {
    private IFileDownload fileDownload = new LocalFileDownload();
    private IParser parser = new JsoupParserImpl();
    private Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void testGetAllLinks() throws Exception {
    }

    @Test
    public void testGetHTMLLinks() throws Exception {
        String url = "http://hzjira.cairenhui.com/issues/?filter=10310";
        String filePath = fileDownload.downloadFile(url);
        Set<String> urlSet = parser.getHTMLLinks(filePath);
        for (String urlLink : urlSet) {
            logger.debug("抓取链接: " + urlLink);
        }
    }

    @Test
    public void testGetIMGLinks() throws Exception {

    }
}