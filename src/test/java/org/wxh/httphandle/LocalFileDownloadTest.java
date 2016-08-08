package org.wxh.httphandle;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class LocalFileDownloadTest {
    private IFileDownload fileDownload = new LocalFileDownload();
    private Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void testGetFileNameByUrl() throws Exception {
        String url = "http://www.**_>ba??>du.com";
        String fileName = fileDownload.getFileNameByUrl(url, "text/html");
        logger.debug("fileName in junitTest is:  " + fileName);
    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testDownloadFile() throws Exception {
        this.fileDownload.downloadFile("http://www.inexus.co/portal.php");
    }
}