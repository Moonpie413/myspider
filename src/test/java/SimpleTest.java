import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */

public class SimpleTest {

    private Logger logger = Logger.getLogger(this.getClass());

    @Test
    public void testLog() {
        logger.debug("logger is ok");
    }

    @Test
    public void StringUtilTest() {
        String testText = "https://www.baidu.com";
        logger.debug(StringUtils.indexOf(testText, "https"));
    }

    @Test
    public void testSSL() {
    }


}
