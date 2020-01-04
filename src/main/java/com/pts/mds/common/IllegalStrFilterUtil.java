package com.pts.mds.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 非法字符检测工具类
 */
public class IllegalStrFilterUtil {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(IllegalStrFilterUtil.class);

    /**
     * 非法字符
     */
    private static final String REGX = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(\\&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§ ";

    /**
     * 对常见的sql注入攻击进行拦截
     *
     * @param input
     * @return
     *  true 表示参数不存在SQL注入风险
     *  false 表示参数存在SQL注入风险
     */
    public static Boolean sqlStrFilter(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.toUpperCase();

        if (input.indexOf("DELETE") >= 0 || input.indexOf("ASCII") >= 0 || input.indexOf("UPDATE") >= 0 || input.indexOf("SELECT") >= 0
                || input.indexOf("'") >= 0 || input.indexOf("SUBSTR(") >= 0 || input.indexOf("COUNT(") >= 0 || input.indexOf(" OR ") >= 0
                || input.indexOf(" AND ") >= 0 || input.indexOf("DROP") >= 0 || input.indexOf("EXECUTE") >= 0 || input.indexOf("EXEC") >= 0
                || input.indexOf("TRUNCATE") >= 0 || input.indexOf("INTO") >= 0 || input.indexOf("DECLARE") >= 0 || input.indexOf("MASTER") >= 0) {
            logger.error("参数发现SQL注入风险：sInput=" + input);
            return false;
        }
        return true;
    }

    /**
     * 对非法字符进行检测
     *
     * @param input
     * @return
     *  true 表示参数不包含非法字符
     *  false 表示参数包含非法字符
     */
    public static Boolean isIllegalStr(String input) {

        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.trim();
        Pattern compile = Pattern.compile(REGX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(input);
        return matcher.find();
    }

}
