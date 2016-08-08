package org.wxh.httphandle;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.wxh.utils.EnvironmentUtil;
import org.wxh.utils.HttpClientUtil;

import java.io.*;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class LocalFileDownload implements IFileDownload {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public String getFileNameByUrl(String url, String contentType) {
        boolean isHttps = false;
        if (StringUtils.indexOf(url, "https") != -1) isHttps = true;
        String fileName = StringUtils.substring(url, isHttps ? 8 : 7);
        if (StringUtils.indexOf(contentType, "html") != -1) {
            fileName = fileName.replaceAll("[\\?/:*<>\"]", "_") + ".html";
            return fileName;
        } else {
            return fileName.replaceAll("[\\?/:*<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") +1);
        }
    }

    @Override
    public void save(byte[] data, String filePath) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(filePath))) {
            for (byte aData : data) {
                outputStream.write(aData);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            logger.error("文件路径" + filePath + "找不到");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(InputStream inputStream, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            int temp;
            while ((temp = inputStream.read()) != -1) {
                outputStream.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String downloadFile(String url) {
        HttpGet get = new HttpGet(url);
        String filePath = null;
        HttpResponse response;
        try {
            response = HttpClientUtil.getInstance().httpGet(url, HttpClientUtil.defaultHeaders);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("请求url" + url + "失败:" + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity();
            filePath = EnvironmentUtil.getDownloadPath()
                    + File.separator + getFileNameByUrl(url, entity.getContentType().getValue());
            this.save(entity.getContent(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
        return filePath;
    }

}
