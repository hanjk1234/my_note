package com.util.shell;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author wei.Li by 14-8-25.
 */
public class ShellUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShellUtil.class);

    public static final String[] ILLEGAL_LETTERS = new String[]{"rm", "ll"};

    /**
     * 校验字符非法性
     *
     * @param arg 校验的字符
     * @return 含有非法字符则返回 true
     */
    private static boolean checkInspectionLegal(String... arg) {

        for (String s : arg) {
            for (String letter : ILLEGAL_LETTERS) {
                if (StringUtils.equals(s, letter)) {
                    LOGGER.error("checkInspectionLegal arg have illegal letters <{}> . return false .", s);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * shell打开文件或者目录
     *
     * @param filePath 文件或者目录地址
     */
    public static void openFile(String filePath) {

        if (checkInspectionLegal(filePath))
            return;
        try {

            Runtime runtime = Runtime.getRuntime();
            runtime.exec("open " + filePath);

        } catch (IOException e) {
            LOGGER.error("openFile error . <{}>", e.getMessage());
        }
    }

    public static void main(String[] args) {
        openFile("/lw");
    }
}
