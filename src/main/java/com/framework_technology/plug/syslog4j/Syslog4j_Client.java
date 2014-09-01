package com.framework_technology.plug.syslog4j;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

/**
 * 客户端发送 log
 *
 * @author lw by 14-7-14.
 */
public class Syslog4j_Client {

    private static SyslogIF syslog = null;


    private static void writeLog(SyslogIF syslog) {
        syslog.error(" --------Log Message --------");
        syslog.critical("-------Log critical ---------");
    }

    private static SyslogIF getSyslog() {
        if (syslog == null) {
            syslog = Syslog.getInstance("udp");
            syslog.getConfig().setHost("192.168.1.105");
            syslog.getConfig().setPort(1514);
            syslog.getConfig().setCharSet("UTF-8");
        }
        return syslog;
    }

    public static void main(String[] args) {
        writeLog(getSyslog());
    }
}
