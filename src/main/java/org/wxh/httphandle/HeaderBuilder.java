package org.wxh.httphandle;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.wxh.utils.PropUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Maroon on 2016/8/8.
 * project: MySpider
 */
public class HeaderBuilder {
    private  static List<Header> headerList = new ArrayList<>();

    static {
        Properties prop = null;
        try {
            prop = PropUtil.getProperties("header");
            Header userAgent = new BasicHeader("User-Agent", prop.getProperty("header.User-Agent"));
            Header accept = new BasicHeader("Accept", prop.getProperty("header.Accept"));
            Header acceptEncoding = new BasicHeader("Accept-Encoding", prop.getProperty(prop.getProperty("header.Accept-Encoding")));
            Header acceptLanguage = new BasicHeader("Accept-Language", prop.getProperty("header.Accept-Language"));
            headerList.add(userAgent);
            headerList.add(accept);
            headerList.add(acceptEncoding);
            headerList.add(acceptLanguage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (prop != null) {
                prop.clear();
            }
        }
    }

    public static List<Header> getHeaderList() {
        return headerList;
    }

    public static Header[] getHeaders() {
        Header[] headers = new Header[getHeaderList().size()];
        for (int i = 0; i < getHeaderList().size(); i++) {
            headers[i] = getHeaderList().get(i);
        }
        return headers;
    }
}
