package com.framework_technology.plug.syslog4j;

import com.google.common.collect.Maps;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

import java.util.Map;

/**
 * 客户端发送 log
 *
 * @author lw by 14-7-14.
 */
public class Syslog4j_Client {


    //缓存 SyslogIF 对象
    private static Map<String, SyslogIF> syslogIFConcurrentMap = Maps.newConcurrentMap();

    private static void writeLog(SyslogIF syslog) {
        syslog.error("触发时间范围[2014-09-18/17:30:15-2014-09-18/17:30:15],告警数量[1]");
        syslog.critical("触发时间范围[2014-09-18/17:30:15-2014-09-18/17:30:15],告警数量[1]");
    }

    private static SyslogIF getSyslog() {

        SyslogIF syslogIF = syslogIFConcurrentMap.get("#");

        if (syslogIF == null) {
            syslogIF = Syslog.getInstance("udp");
            syslogIF.getConfig().setHost("127.0.0.1");
            syslogIF.getConfig().setPort(1514);
            syslogIF.getConfig().setCharSet("UTF-8");
        }
        return syslogIF;
    }

    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    writeLog(getSyslog());
                }
            }
        }).start();
    }
}
